package mainservice.participationrequest.entity;

import lombok.*;
import mainservice.event.entity.Event;
import mainservice.participationrequest.dto.ParticipationRequestEnum;
import mainservice.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participation_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_request_id")
    Long id;
    @Column(name = "participation_request_created")
    LocalDateTime created;
    @JoinColumn(name = "participation_request_event_id")
    @OneToOne
    Event event;
    @JoinColumn(name = "participation_request_requester_id")
    @ManyToOne
    User requester;
    @Column(name = "participation_request_status")
    @Enumerated(EnumType.STRING)
    ParticipationRequestEnum status;

}
