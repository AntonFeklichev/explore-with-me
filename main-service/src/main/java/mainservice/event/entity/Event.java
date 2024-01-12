package mainservice.event.entity;

import lombok.*;
import mainservice.category.entity.Category;
import mainservice.location.entity.Location;
import mainservice.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "events_id")
    private Long id;
    @Column(name = "events_annotation", length = 2000)
    private String annotation;
    @JoinColumn(name = "events_category")
    @ManyToOne
    private Category category;
    @Column(name = "events_confirmed_requests")
    @Builder.Default
    private Integer confirmedRequests = 0;
    @Column(name = "events_created_on")
    private LocalDateTime createdOn;
    @Column(name = "events_description", length = 7000)
    private String description;
    @Column(name = "events_event_date")
    private LocalDateTime eventDate;
    @JoinColumn(name = "events_initiator")
    @ManyToOne
    private User initiator;
    @JoinColumn(name = "events_location")
    @ManyToOne
    private Location location;
    @Column(name = "events_paid")
    private Boolean paid;
    @Column(name = "events_participant_limit")
    private Integer participantLimit;
    @Column(name = "events_published_on")
    private LocalDateTime publishedOn;
    @Column(name = "events_request_moderation")
    private Boolean requestModeration;
    @Column(name = "events_state")
    @Enumerated(value = EnumType.STRING)
    private EventStateEnum state;
    @Column(name = "events_title")
    private String title;
    @Column(name = "events_views")
    private Long views;


}
