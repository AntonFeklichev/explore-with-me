package mainservice.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    @Column(name = "users_name")
    private String name;
    @Column(name = "users_email")
    private String email;

}
