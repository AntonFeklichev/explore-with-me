package mainservice.compilations.entity;

import lombok.*;
import mainservice.event.entity.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Compilation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compilation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    Long id;

    @JoinTable(name = "compilation_event")
    @ManyToMany
    List<Event> events;

    @Column(name = "pinned")
    Boolean pinned;

    @Column(name = "title")
    String title;
}
