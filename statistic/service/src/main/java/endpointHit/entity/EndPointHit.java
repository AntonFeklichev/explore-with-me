package endpointHit.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "endpoint_hit")
public class EndPointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endpoint_hit_id")
    private Long id;

    @Column(name = "endpoint_hit_app")
    private String app;

    @Column(name = "endpoint_hit_uri")
    private String uri;

    @Column(name = "endpoint_hit_ip")
    private String ip;

    @Column(name = "endpoint_hit_timestamp")
    private LocalDateTime timestamp;

}
