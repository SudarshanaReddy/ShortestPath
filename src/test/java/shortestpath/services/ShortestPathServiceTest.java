package shortestpath.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shortestpath.algorithmutil.GalaxyGraph;
import shortestpath.model.Galaxy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class ShortestPathServiceTest {

    @InjectMocks
    ShortestPathService shortestPathService;

    @Test
    public void calculateShortestPath() {
    }

    @Test
    public void generateGraph() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class shortestPathServiceClass =  shortestPathService.getClass();

        Method method = shortestPathServiceClass.getDeclaredMethod("generateGraph",Iterable.class);

        method.setAccessible(true);

        List<Galaxy> galaxyList = new LinkedList<Galaxy>();

        Galaxy galaxy = new Galaxy();
        galaxy.setRouteId(1);
        galaxy.setOriginPlanet("Earth");
        galaxy.setDestinationPlanet("Pluto");
        galaxy.setDistance(10);
        galaxy.setTraffic(10000);

        GalaxyGraph galaxyGraph = (GalaxyGraph) method.invoke(shortestPathService,galaxyList);

        Assert.assertEquals("Earth",galaxy.getOriginPlanet());



    }
}