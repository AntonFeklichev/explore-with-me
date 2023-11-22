package mainservice.category.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    Long id;
    @Column(name = "category_name")
    String name;
}
