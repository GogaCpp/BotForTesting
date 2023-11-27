package jenya.gogacpypy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="collections", schema = "alldata")
@Data
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @JsonIgnoreProperties(value = {"collections","groups"}, allowSetters = true)
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "collections_to_questions", schema = "alldata",
            joinColumns = { @JoinColumn(name = "collection_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Question> questions;

    @JsonIgnoreProperties(value = {"collections"}, allowSetters = true)
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "disciplines_to_collections", schema = "alldata",
            joinColumns = { @JoinColumn(name = "collection_id") },
            inverseJoinColumns = { @JoinColumn(name = "discipline_id") })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Discipline> disciplines;

    @JsonIgnoreProperties(value = {"collection","questions"}, allowSetters = true)
    @OneToMany(mappedBy = "collection")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Group> groups;

    @JsonIgnoreProperties(value = {"collection"}, allowSetters = true)
    @OneToMany(mappedBy = "collection")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Test> tests;
}
