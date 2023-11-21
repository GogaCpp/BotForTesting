package jenya.gogacpypy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="disciplines_to_collection", schema = "alldata")
@Data
public class DisciplinesToCollections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="discipline_id")
    private long disciplineId;

    @Column(name="collection_id")
    private long collectionId;
}
