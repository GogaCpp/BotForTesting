package com.bot.botfortesting.repository;

import com.bot.botfortesting.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAnswerByQuestionId(long questionId);

    Answer getAnswerByNameAndQuestionId(String answer,long questionId);
}
