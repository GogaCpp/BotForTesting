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

    @Column(name="id_disciplines")
    private long disciplineId;

    @Column(name="id_collection")
    private long collectionId;
}
