package shortestpath.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shortestpath.model.Galaxy;

@Repository
public interface GalaxyRepository extends CrudRepository<Galaxy, Double> {

}


