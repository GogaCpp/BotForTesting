package jenya.gogacpypy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

//    @JsonIgnoreProperties("groups")
    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "questions_to_group", schema = "alldata",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") })
    private List<Question> questions;


}
