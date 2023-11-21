package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.CollectionsToQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CollectionsToQuestionsRepository extends JpaRepository<CollectionsToQuestions, Long> {

    List<CollectionsToQuestions> findByCollectionIdAndQuestionId(long collectionId, long questionId);

}
