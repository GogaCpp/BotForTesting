package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.QuestionsToGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsToGroupsRepository extends JpaRepository<QuestionsToGroups, Long> {
}
