package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.CollectionsToQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionsToQuestionsRepository extends JpaRepository<CollectionsToQuestions, Long> {
}
