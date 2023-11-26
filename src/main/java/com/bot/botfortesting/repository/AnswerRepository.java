package com.bot.botfortesting.repository;

import com.bot.botfortesting.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAnswersByQuestionId(long questionId);
    List<Answer> findAnswersByQuestionIdAndCorrect(long questionId,boolean correct);


    Answer getAnswerByNameAndQuestionId(String answer,long questionId);
}
