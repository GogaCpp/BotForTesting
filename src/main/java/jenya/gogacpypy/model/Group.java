package jenya.gogacpypy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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

//    @Column(name="collection_id",insertable=false, updatable=false)
//    private long collectionId;


    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @JsonIgnoreProperties(value = {"groups","collections"}, allowSetters = true)
    @ManyToMany(cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "groups_to_questions", schema = "alldata",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Question> questions;

//    @JsonIgnoreProperties(value = {"groups"}, allowSetters = true)
//    @ManyToMany(cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "tests_to_groups", schema = "alldata",
//            joinColumns = { @JoinColumn(name = "group_id") },
//            inverseJoinColumns = { @JoinColumn(name = "test_id") })
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Test> tests;


}
