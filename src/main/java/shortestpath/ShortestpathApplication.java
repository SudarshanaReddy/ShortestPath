package shortestpath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shortestpath.algorithmutil.DijkstraShortestPathAlgorithm;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class ShortestpathApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShortestpathApplication.class, args);

        Endpoint.publish("http://localhost:8082/algorithm",new DijkstraShortestPathAlgorithm());
    }

}
