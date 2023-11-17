package mainservice.location.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Data
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
