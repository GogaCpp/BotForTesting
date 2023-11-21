package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.GroupsToQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsToQuestionsRepository extends JpaRepository<GroupsToQuestions, Long> {
    List<GroupsToQuestions> findByGroupIdAndQuestionId(long groupId, long questionId);
}
