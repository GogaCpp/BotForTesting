package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.TestsToGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsToGroupsRepository extends JpaRepository<TestsToGroups, Long> {
}
