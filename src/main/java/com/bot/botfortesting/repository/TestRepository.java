package com.bot.botfortesting.repository;

import com.bot.botfortesting.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Test findByName(String name);
}
