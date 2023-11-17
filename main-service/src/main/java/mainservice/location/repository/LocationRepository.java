package mainservice.location.repository;

import mainservice.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT l " +
           "FROM Location AS l " +
           "WHERE l.lat = :lat " +
           "AND l.log = :log"
    )
    Optional<Location> findByLatAndLog(Double lat, Double log);

}
