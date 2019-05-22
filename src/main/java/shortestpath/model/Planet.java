package shortestpath.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Planet {

    private String originPlanet;
    private String destinationPlanet;
    private double distance;
    private double traffic;
}
