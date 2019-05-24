package shortestpath.services;

import org.springframework.stereotype.Service;
import shortestpath.algorithmutil.DijkstraShortestPathAlgorithm;
import shortestpath.algorithmutil.GalaxyGraph;
import shortestpath.algorithmutil.Planet;
import shortestpath.algorithmutil.PlanetEdges;
import shortestpath.model.Galaxy;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShortestPathService implements ShortestPathInterface {

    private List<PlanetEdges> planetEdgesList = new ArrayList<>();

    @Override
    public List<Planet> calculateShortestPathWithoutTraffic(Iterable<Galaxy> galaxy,
                                                            String startPlanet, String targetPlanet) {

        GalaxyGraph galaxyGraph = generateGraph(galaxy);

        DijkstraShortestPathAlgorithm algo = new DijkstraShortestPathAlgorithm(galaxyGraph);

        algo.execute(new Planet(startPlanet));

        return algo.getPath(new Planet(targetPlanet));

    }


    /**
     * This method creates a connecting graph that represents a galaxy
     * param galaxy - Iterable galaxy object
     *
     * @return the GalaxyGraph Object
     */
    private GalaxyGraph generateGraph(Iterable<Galaxy> galaxy) {


        galaxy.forEach(galaxyObject -> {

            String sourcePlanet = galaxyObject.getOriginPlanet();
            String destinationPlanet = galaxyObject.getDestinationPlanet();
            double distance = galaxyObject.getDistance();

            PlanetEdges planetEdges = new PlanetEdges(new Planet(sourcePlanet), new Planet(destinationPlanet), distance);

            planetEdgesList.add(planetEdges);

        });

        return new GalaxyGraph(planetEdgesList);

    }
}
