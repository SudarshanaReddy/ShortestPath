package shortestpath.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import shortestpath.constants.GalaxyExceptionConstants;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.model.SuccessIndicator;
import shortestpath.services.GalaxyInterface;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@Api("Galaxy-CRUD-Operations")
public class GalaxyController {


    private GalaxyInterface galaxyService;

    @Autowired
    public GalaxyController(GalaxyInterface galaxyService){

        this.galaxyService = galaxyService;
    }

    @PostMapping(path="/creategalaxy")
    public List<Galaxy> createGalaxy() throws IOException {

        return galaxyService.persistGalaxyToDerby();

    }

    @GetMapping(path = "/getgalaxy")
    public Iterable<Galaxy> getGalaxy() {

        Iterable<Galaxy> galaxies = galaxyService.getGalaxy();

        if (ObjectUtils.isEmpty(galaxies)) {
            throw new GalaxyException(GalaxyExceptionConstants.GALAXY_NOT_FOUND);
        }

        return galaxies;
    }

    @PostMapping(path="/addplanet", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessIndicator addPlanet(@RequestBody Galaxy planet){

        if(ObjectUtils.isEmpty(planet)){
            throw new GalaxyException("Pass the correct planet to add");
        }

        return galaxyService.addPlanet(planet);
    }

    @PostMapping(path="/deleteplanet", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessIndicator deletePlanet(@RequestBody Galaxy planet){

        if(ObjectUtils.isEmpty(planet)){
            throw new GalaxyException("Pass the correct planet to add");
        }

        return galaxyService.deletePlanet(planet);
    }

    @GetMapping(path="/getAllPlanets")
    public List<String> getAllPlanets(){

        return galaxyService.getAllPlanets();

    }

}
