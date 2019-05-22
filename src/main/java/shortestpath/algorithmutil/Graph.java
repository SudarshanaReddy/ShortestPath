package shortestpath.algorithmutil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

    private List<Planet> planets;
    private List<Edge> edges;

}
