package shortestpath.algorithmutil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetEdges {
    private String id;
    private Planet source;
    private Planet destination;
    private double distance;
    private double traffic;

    public PlanetEdges(Planet source, Planet destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

}