package mainservice.event.repository;

import mainservice.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CustomEventRepository {

    @Query("SELECT e " +
           "FROM Event AS e " +
           "WHERE e.initiator.id = :userId")
    Page<Event> getEventsByUserId(Long userId, Pageable pageRequest);

   /* @Query("SELECT e " +
           "FROM Event AS e" +
           "WHERE e.initiator.id = :userId" +
           "AND e.id = :eventId")
    Event getEventByUserIdAndEventId(Long userId, Long eventId); */

@Query("SELECT e.initiator.id " +
       "FROM Event as e " +
       "WHERE e.id = :eventId")
   Long findInitiatorIdByEventId(Long eventId);

}
