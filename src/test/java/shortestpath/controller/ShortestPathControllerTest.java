package shortestpath.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shortestpath.exceptions.GalaxyException;
import shortestpath.services.GalaxyService;
import shortestpath.services.ShortestPathService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ShortestPathControllerTest {

    @Mock
    GalaxyService galaxyService;

    @Mock
    ShortestPathService shortestPathService;

    ShortestPathController shortestPathController
            = new ShortestPathController(galaxyService,shortestPathService);

    @Test(expected = GalaxyException.class)
    public void shortestPathTest_OriginPlanet_IS_NULL() {

        shortestPathController.shortestPath(null,"B");
    }

    @Test(expected = GalaxyException.class)
    public void shortestPathTest_DestinationPlanet_IS_NULL() {

        shortestPathController.shortestPath("A",null);
    }

    @Test(expected = GalaxyException.class)
    public void shortestPathTest_OriginPlanet_DestinationPlanet_IS_SAME() {

        shortestPathController.shortestPath("A","A");
    }


}