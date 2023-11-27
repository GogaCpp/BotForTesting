package jenya.gogacpypy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="questions", schema = "alldata")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="is_valid")
    private boolean isValid;

    @JsonIgnoreProperties(value = {"questions","groups"}, allowSetters = true)
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "collections_to_questions", schema = "alldata",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "collection_id") })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Collection> collections;

    @JsonIgnoreProperties(value = {"questions"}, allowSetters = true)
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "groups_to_questions", schema = "alldata",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Group> groups;

    @JsonIgnoreProperties(value = {"question"}, allowSetters = true)
    @OneToMany(mappedBy = "question")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Answer> answers;
}
