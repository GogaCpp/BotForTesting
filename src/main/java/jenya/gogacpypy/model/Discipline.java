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
@Table(name="disciplines", schema = "alldata")
@Data
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "disciplines_to_collection",
            joinColumns = { @JoinColumn(name = "id_disciplines") },
            inverseJoinColumns = { @JoinColumn(name = "id_collection") })
    private Set<Collection> collections = new HashSet<>();
}
