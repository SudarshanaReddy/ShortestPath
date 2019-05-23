package shortestpath.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.model.SuccessIndicator;
import shortestpath.repository.GalaxyRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class GalaxyServiceTest {

    @InjectMocks
    GalaxyService galaxyService;

    @Mock
    GalaxyRepository galaxyRepository;

    @Test
    public void persistGalaxyToDB() {


    }

    @Test
    public void addPlanet() {

        Galaxy newPlanet = new Galaxy(1,"A","C",10.0,12.0);

        Mockito.doReturn(newPlanet).when(galaxyRepository).save(Mockito.any());

        SuccessIndicator successIndicator = galaxyService.addPlanet(newPlanet);

        Assert.assertEquals(201,successIndicator.getSuccessIndicatorFlag());

    }

    @Test(expected = GalaxyException.class)
    public void addPlanet_ISNULL() {

        Mockito.doReturn(null).when(galaxyRepository).save(Mockito.any());

        SuccessIndicator successIndicator = galaxyService.addPlanet(new Galaxy());

    }
}