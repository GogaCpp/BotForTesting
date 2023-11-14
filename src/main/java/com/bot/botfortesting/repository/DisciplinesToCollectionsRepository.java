package com.bot.botfortesting.repository;

import com.bot.botfortesting.model.DisciplinesToCollections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinesToCollectionsRepository extends JpaRepository<DisciplinesToCollections, Long> {
}
