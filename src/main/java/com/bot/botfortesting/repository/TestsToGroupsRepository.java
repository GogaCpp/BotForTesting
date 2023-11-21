package com.bot.botfortesting.repository;

import com.bot.botfortesting.model.TestsToGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestsToGroupsRepository extends JpaRepository<TestsToGroups, Long> {


    List<TestsToGroups> findAllByTestId(long id);
}
