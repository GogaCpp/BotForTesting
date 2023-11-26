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
@Table(name="test", schema = "alldata")
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

//    @Column(name="id_collection")
//    private long collectionId;

    @ManyToOne
    @JoinColumn(name="collection_id")
    private Collection collection;


//    @JsonIgnoreProperties(value = {"tests"}, allowSetters = true)
//    @ManyToMany(
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "tests_to_groups",  schema = "alldata",
//            joinColumns = { @JoinColumn(name = "test_id") },
//            inverseJoinColumns = { @JoinColumn(name = "group_id") })
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Group> groups;
}
