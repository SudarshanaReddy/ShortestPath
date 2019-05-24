package shortestpath.algorithmutil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalaxyGraph {

    private List<PlanetEdges> planetEdges;

}
