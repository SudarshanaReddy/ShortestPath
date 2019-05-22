package shortestpath.services;

import shortestpath.model.Galaxy;

public interface GalaxyInterface {

    public boolean persistGalaxyToDerby();

    public Galaxy getGalaxy();

}
