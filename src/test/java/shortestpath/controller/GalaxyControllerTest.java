package shortestpath.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.services.GalaxyService;

@RunWith(SpringJUnit4ClassRunner.class)
public class GalaxyControllerTest {

    @Mock
    GalaxyService galaxyService;

    @Test
    public void createGalaxy() {

    }

    @Test(expected = shortestpath.exceptions.GalaxyException.class)
    public void addPlanet_Planet_Passed_IS_NULL() {

        GalaxyController controller = new GalaxyController(galaxyService);

        controller.addPlanet(null);

    }

    @Test
    public void addPlanet_Planet_From_Service_IS_NULL() {

        GalaxyController controller = new GalaxyController(galaxyService);

        Mockito.doReturn(null).when(galaxyService).addPlanet(Mockito.any());

        Assert.assertNull("Planet is null from service", controller.addPlanet(new Galaxy()));

    }

    @Test(expected = GalaxyException.class)
    public void deletePlanet_Planet_Passed_IS_NULL() {

        GalaxyController controller = new GalaxyController(galaxyService);

        controller.deletePlanet(null);

    }

    @Test
    public void deletePlanet_Planet_From_Service_IS_NULL() {

        GalaxyController controller = new GalaxyController(galaxyService);

        Mockito.doReturn(null).when(galaxyService).addPlanet(Mockito.any());

        Assert.assertNull("Planet is null from service", controller.deletePlanet(new Galaxy()));

    }



}