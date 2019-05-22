package shortestpath.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Galaxy {

    @Id
    private double routeId;
    private String originPlanet;
    private String destinationPlanet;
    private double distance;
    private double traffic;

}