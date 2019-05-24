package shortestpath.algorithmutil;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.jws.WebService;
import java.util.*;

@WebService(name = "ShortestPathAlgorithm", endpointInterface = "shortestpath.algorithmutil.DijkstraShortestPathAlgorithm")
@NoArgsConstructor
@AllArgsConstructor
public class DijkstraShortestPathAlgorithm {

    private List<PlanetEdges> planetEdges;
    private Set<Planet> processedPlanets;
    private Set<Planet> unProcessedPlanets;
    private Map<Planet, Planet> predecessors;
    private Map<Planet, Double> distance;


    public DijkstraShortestPathAlgorithm(GalaxyGraph galaxyGraph) {
        // create a copy of the array so that we can operate on this array
        this.planetEdges = new ArrayList<>(galaxyGraph.getPlanetEdges());
    }

    /**
     * Thsi method finds the shortest path from source to every other planet in the galaxy
     *
     * @param source - represents the source planet
     */
    public void execute(Planet source) {
        processedPlanets = new HashSet<>();
        unProcessedPlanets = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0D);
        unProcessedPlanets.add(source);
        while (unProcessedPlanets.size() > 0) {
            Planet node = getMinimumBetweenPlanets(unProcessedPlanets);
            processedPlanets.add(node);
            unProcessedPlanets.remove(node);
            findMinimalDistancePlanets(node);
        }
    }

    /**
     * This method finds the minimal distance planets from a given planet
     *
     * @param planet - represents a planet
     */
    private void findMinimalDistancePlanets(Planet planet) {
        List<Planet> adjacentPlanets = getNeighborPlanets(planet);
        for (Planet targetPlanet : adjacentPlanets) {
            if (getShortestDistance(targetPlanet) > getShortestDistance(planet)
                    + getDistance(planet, targetPlanet)) {
                distance.put(targetPlanet, getShortestDistance(planet)
                        + getDistance(planet, targetPlanet));
                predecessors.put(targetPlanet, planet);
                unProcessedPlanets.add(targetPlanet);
            }
        }

    }

    /**
     * @param sourcePlanet      - represents the source planet
     * @param destinationPlanet - represents the destination planet
     * @return double - returns the distance between given source and target planets
     */
    private double getDistance(Planet sourcePlanet, Planet destinationPlanet) {

        double edgeDistance = 0D;
        for (PlanetEdges planetEdgesObject : this.planetEdges) {
            if (planetEdgesObject.getSource().equals(sourcePlanet)
                    && planetEdgesObject.getDestination().equals(destinationPlanet)) {
                edgeDistance = planetEdgesObject.getDistance();
            }
        }
        return edgeDistance;
    }

    /**
     * @param sourcePlanet - represents a source planet
     * @return List of neighbour planets for a given planet
     */
    private List<Planet> getNeighborPlanets(Planet sourcePlanet) {
        List<Planet> neighbors = new ArrayList<>();
        for (PlanetEdges planetEdgesObject : this.planetEdges) {
            if (planetEdgesObject.getSource().equals(sourcePlanet)
                    && !isSettled(planetEdgesObject.getDestination())) {
                neighbors.add(planetEdgesObject.getDestination());
            }
        }
        return neighbors;
    }

    private Planet getMinimumBetweenPlanets(Set<Planet> planets) {
        Planet minimum = null;
        for (Planet planet : planets) {
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

    /**
     * This method helps in finding the whether aa planet is processed or not
     *
     * @param planet - represents a planet
     * @return boolean
     */
    private boolean isSettled(Planet planet) {
        return processedPlanets.contains(planet);
    }

    /**
     * @param destination - reperesents a destination planet
     * @return double
     */
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
