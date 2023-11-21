package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "tests_to_groups",  schema = "alldata",
//            joinColumns = { @JoinColumn(name = "test_id") },
//            inverseJoinColumns = { @JoinColumn(name = "group_id") })
//    private List<Group> groups;
}
