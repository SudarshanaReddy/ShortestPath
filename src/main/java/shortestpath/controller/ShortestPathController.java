package shortestpath.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shortestpath.algorithmutil.Planet;
import shortestpath.constants.GalaxyExceptionConstants;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.services.GalaxyInterface;
import shortestpath.services.GalaxyService;
import shortestpath.services.ShortestPathInterface;
import shortestpath.services.ShortestPathService;

import java.util.List;

@CrossOrigin
@Api(tags = "Shortest Path")
@RestController
public class ShortestPathController {

    private GalaxyInterface galaxyService;

    private ShortestPathInterface shortestPathService;

    @Autowired
    public ShortestPathController(GalaxyInterface galaxyService, ShortestPathInterface shortestPathService){
        this.galaxyService = galaxyService;
        this.shortestPathService = shortestPathService;
    }

    @ApiOperation(tags="calculates shortest path", value="Calculates the shortest path from given source planet to destination planet")
    @GetMapping(path="/calculateshortestpath/{originPlanet}/{destinationPlanet}")
    public List<Planet> shortestPath(@PathVariable("originPlanet") String originPlanet,
                                     @PathVariable("destinationPlanet") String destinationPlanet){

       if(originPlanet == null){
          throw new GalaxyException(GalaxyExceptionConstants.ORIGIN_PLANET_IS_NULL);
       }
       else if(destinationPlanet == null){
          throw new GalaxyException(GalaxyExceptionConstants.DESTINATION_PLANET_IS_NULL);
       }
       else if(originPlanet.equalsIgnoreCase(destinationPlanet)){
          throw new GalaxyException(GalaxyExceptionConstants.SOURCE_PLANET_AND_DESTINATION_PLANET_CANNOT_BE_SAME);
       }

       Iterable<Galaxy> galaxy = galaxyService.getGalaxy();

       System.err.println("Shortest path interface called...");
       return shortestPathService.calculateShortestPathWithoutTraffic(galaxy, originPlanet, destinationPlanet);

    }

}
