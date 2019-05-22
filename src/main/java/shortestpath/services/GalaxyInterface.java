package shortestpath.services;

import shortestpath.model.Galaxy;
import shortestpath.model.SuccessIndicator;

import java.util.List;

public interface GalaxyInterface {

    List<Galaxy> persistGalaxyToDerby();

    Iterable<Galaxy> getGalaxy();

    SuccessIndicator addPlanet(Galaxy planet);

    SuccessIndicator deletePlanet(Galaxy planet);

}
