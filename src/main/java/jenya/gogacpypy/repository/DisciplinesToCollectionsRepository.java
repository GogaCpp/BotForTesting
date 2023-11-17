package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.DisciplinesToCollections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinesToCollectionsRepository extends JpaRepository<DisciplinesToCollections, Long> {
}
