package shortestpath.algorithmutil;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.jws.WebService;
import java.util.*;

@WebService(name="ShortestPathAlgorithm", endpointInterface = "shortestpath.algorithmutil.DijkstraAlgorithm")
@NoArgsConstructor
@AllArgsConstructor
public class DijkstraAlgorithm {

    private List<Planet> nodes;
    private List<Edge> edges;
    private Set<Planet> settledNodes;
    private Set<Planet> unSettledNodes;
    private Map<Planet, Planet> predecessors;
    private Map<Planet, Double> distance;


    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<>(graph.getVertices());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public void execute(Planet source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0D);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Planet node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Planet node) {
        List<Planet> adjacentNodes = getNeighbors(node);
        for (Planet target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private double getDistance(Planet node, Planet target) {

        double edgeDistance = 0D;
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                edgeDistance = edge.getDistance();
            }
        }
        return edgeDistance;
    }

    private List<Planet> getNeighbors(Planet node) {
        List<Planet> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Planet getMinimum(Set<Planet> vertices) {
        Planet minimum = null;
        for (Planet planet : vertices) {
            if (minimum == null) {
                minimum = planet;
            } else {
                if (getShortestDistance(planet) < getShortestDistance(minimum)) {
                    minimum = planet;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Planet planet) {
        return settledNodes.contains(planet);
    }

    private double getShortestDistance(Planet destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public List<Planet> getPath(Planet target) {
        LinkedList<Planet> path = new LinkedList<>();
        Planet step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return path;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
