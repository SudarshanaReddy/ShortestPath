package shortestpath.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shortestpath.model.Galaxy;

import java.util.List;

@Repository
public interface GalaxyRepository extends CrudRepository<Galaxy, String> {

}


