package shortestpath.services;

import shortestpath.algorithmutil.Planet;
import shortestpath.model.Galaxy;

import java.util.List;

public interface ShortestPathInterface {

    List<Planet> calculateShortestPathWithoutTraffic(Iterable<Galaxy> galaxy,
                                                     String startPlanet, String targetPlanet);
}
