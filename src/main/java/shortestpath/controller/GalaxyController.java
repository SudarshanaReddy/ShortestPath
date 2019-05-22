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
import shortestpath.services.GalaxyService;
import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "Galaxy Controller")
public class GalaxyController {

    private GalaxyService galaxyService;

    @Autowired
    public GalaxyController(GalaxyService galaxyService){

        this.galaxyService = galaxyService;
    }

    @PostMapping(path="/creategalaxy")
    public List<Galaxy> generateGraph()  {

        return galaxyService.persistGalaxyToDB();

    }

    @GetMapping(path = "/getgalaxy")
    public Iterable<Galaxy> getGalaxy() {

        Iterable<Galaxy> galaxies = galaxyService.getGalaxy();

        if (ObjectUtils.isEmpty(galaxies)) {
            throw new GalaxyException(GalaxyExceptionConstants.galaxyNotFound);
        }

        return galaxies;
    }

    @PostMapping(path="/addplanet", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessIndicator addGalaxy(@RequestBody Galaxy planet){

        if(planet == null){
            throw new GalaxyException("Pass the correct planet to add");
        }

        return galaxyService.addPlanet(planet);
    }

}
