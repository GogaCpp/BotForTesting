package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="groups", schema = "alldata")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="id_collection")
    private long collectionId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "questions_to_group",
            joinColumns = { @JoinColumn(name = "id_group") },
            inverseJoinColumns = { @JoinColumn(name = "id_question") })
    private Set<Question> questions = new HashSet<>();


}
