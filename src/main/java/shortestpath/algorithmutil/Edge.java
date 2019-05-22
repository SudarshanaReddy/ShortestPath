package shortestpath.algorithmutil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    private String id;
    private Planet source;
    private Planet destination;
    private double distance;
    private double traffic;

    public Edge(Planet source, Planet destination, double distance){
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

}