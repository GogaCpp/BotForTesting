package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.Test;
import jenya.gogacpypy.model.TestsToGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestsToGroupsRepository extends JpaRepository<TestsToGroups, Long> {
    List<TestsToGroups> findByTest(Test test);

    List<TestsToGroups> findByTestId(long id);
}
