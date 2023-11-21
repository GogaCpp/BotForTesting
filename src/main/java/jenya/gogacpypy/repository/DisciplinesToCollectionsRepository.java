package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.DisciplinesToCollections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinesToCollectionsRepository extends JpaRepository<DisciplinesToCollections, Long> {
    List<DisciplinesToCollections> findByCollectionIdAndDisciplineId(long collectionId, long disciplineId);
}
