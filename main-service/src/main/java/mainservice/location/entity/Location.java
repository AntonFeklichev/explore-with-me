package mainservice.location.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    Long id;
    @Column(name = "location_lat")
    Double lat;
    @Column(name = "location_lon")
    Double lon;
}
