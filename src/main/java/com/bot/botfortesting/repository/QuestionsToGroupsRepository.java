package com.bot.botfortesting.repository;

import com.bot.botfortesting.model.QuestionsToGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsToGroupsRepository extends JpaRepository<QuestionsToGroups, Long> {

    List<QuestionsToGroups> findAllByGroupId(long id);
}
