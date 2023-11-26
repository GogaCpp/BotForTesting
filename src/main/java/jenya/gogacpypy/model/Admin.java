package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="admins", schema = "alldata")
@Data
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

//    @Column(name = "id_university",nullable = false)
//    private Long universityId;

    @ManyToOne
    @JoinColumn(name="university_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private University university;

}
