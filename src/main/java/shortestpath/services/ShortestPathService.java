package shortestpath.services;

import org.springframework.stereotype.Service;
import shortestpath.algorithmutil.DijkstraAlgorithm;
import shortestpath.algorithmutil.Edge;
import shortestpath.algorithmutil.Graph;
import shortestpath.algorithmutil.Planet;
import shortestpath.model.Galaxy;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShortestPathService {

    private List<Planet> planetList = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();

    public List<Planet> calculateShortestPath(Iterable<Galaxy> galaxy, String startPlanet, String targetPlanet) {

        Graph graph = generateGraph(galaxy);

        DijkstraAlgorithm algo = new DijkstraAlgorithm(graph);

        algo.execute(new Planet(startPlanet));

        return algo.getPath(new Planet(targetPlanet));

    }

   Graph generateGraph(Iterable<Galaxy> galaxy){

       for(int i=65;i<90;i++){
           Character name = (char)i;
           Planet planet = new Planet(name.toString());
           planetList.add(planet);
       }

       galaxy.forEach(galaxyObject ->{

           String sourcePlanet = galaxyObject.getOriginPlanet();
           String destinationPlanet = galaxyObject.getDestinationPlanet();
           double distance = galaxyObject.getDistance();

           Edge edge = new Edge(new Planet(sourcePlanet),new Planet(destinationPlanet),distance);

           edgeList.add(edge);

       });

       return new Graph(planetList,edgeList);

   }
}
