package shortestpath.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shortestpath.algorithmutil.Planet;
import shortestpath.constants.GalaxyExceptionConstants;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.services.GalaxyService;
import shortestpath.services.ShortestPathService;

import java.util.List;

@CrossOrigin
@Api(tags = "Shortest Path")
@RestController
public class ShortestPathController {

    private GalaxyService galaxyService;

    private ShortestPathService shortestPathService;

    @Autowired
    public ShortestPathController(GalaxyService galaxyService, ShortestPathService shortestPathService){
        this.galaxyService = galaxyService;
        this.shortestPathService = shortestPathService;
    }

    @GetMapping(path="/calculateshortestpath/{originPlanet}/{destinationPlanet}")
    public List<Planet> shortestPath(@PathVariable("originPlanet") String originPlanet,
                                     @PathVariable("destinationPlanet") String destinationPlanet){

       if(originPlanet == null){
          throw new GalaxyException(GalaxyExceptionConstants.originPlanetException);
       }
       else if(destinationPlanet == null){
          throw new GalaxyException(GalaxyExceptionConstants.destinationPlanetException);
       }
       else if(originPlanet.equalsIgnoreCase(destinationPlanet)){
          throw new GalaxyException(GalaxyExceptionConstants.sourceDestinationException);
       }


       Iterable<Galaxy> galaxy = galaxyService.getGalaxy();

       return shortestPathService.calculateShortestPath(galaxy, originPlanet, destinationPlanet);

    }

}
