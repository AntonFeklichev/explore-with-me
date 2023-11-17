package mainservice.event.entity;

import lombok.*;
import mainservice.category.entity.Category;
import mainservice.location.entity.Location;
import mainservice.user.entity.User;

import javax.persistence.*;

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
    @Column(name = "events_annotation")
    private String annotation;
    @Column(name = "events_category")
    private Category category;
    @Column(name = "events_confirmed_requests")
    private Integer confirmedRequests;
    @Column(name = "events_created_on")
    private String createdOn;
    @Column(name = "events_description")
    private String description;
    @Column(name = "events_event_date")
    private String eventDate;
    @Column(name = "events_initiator")
    private User initiator;
    @Column(name = "events_location")
    private Location location;
    @Column(name = "events_paid")
    private Boolean paid;
    @Column(name = "events_participant_limit")
    private Integer participantLimit;
    @Column(name = "events_published_on")
    private String publishedOn;
    @Column(name = "events_request_moderation")
    private Boolean requestModeration;
    @Column(name = "events_state")
    private EventStateEnum state;
    @Column(name = "events_title")
    private String title;
    @Column(name = "events_views")
    private Long views;


}
