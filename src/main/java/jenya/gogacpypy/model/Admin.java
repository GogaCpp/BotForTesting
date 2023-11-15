package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="admin",schema="allData")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="pass")
    private String pass;

    @Column(name="login")
    private String login;

    @Column(name = "id_university",nullable = false)
    private Long universityId;
}
