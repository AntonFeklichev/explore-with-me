package mainservice.participationrequest.repository;

import mainservice.event.entity.Event;
import mainservice.participationrequest.dto.ParticipationRequestEnum;
import mainservice.participationrequest.entity.ParticipationRequest;
import mainservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    @Query("SELECT p " +
           "FROM ParticipationRequest p " +
           "WHERE p.event.id = :eventId")
    List<ParticipationRequest> findAllByEventId(Long eventId);


    @Transactional
    @Modifying
    @Query("UPDATE ParticipationRequest p " +
           "SET p.status = :newStatus " +
           "WHERE p.event.id = :eventId " +
           "AND p.status = 'PENDING'")
    int updateStatusByEventIdAndPending(Long eventId, ParticipationRequestEnum newStatus);


    @Query("SELECT p " +
           "FROM ParticipationRequest p " +
           "WHERE p.requester.id = :userId " +
           "ORDER BY p.event.eventDate ASC")
    List<ParticipationRequest> findAllByUserId(Long userId);


    Boolean existsByEventAndRequester(Event event, User user);

}
