package com.bot.botfortesting.service;

import com.bot.botfortesting.model.Answer;
import com.bot.botfortesting.model.Question;
import lombok.Data;


@Data
public class CurrentAnswer {
    private long questionId;
    private long answerId;

    public CurrentAnswer(long questionId,long answerId){
        this.questionId=questionId;
        this.answerId=answerId;
    }

}
