package com.bot.botfortesting.service;

import com.bot.botfortesting.model.Answer;
import com.bot.botfortesting.model.Question;
import lombok.Data;


@Data
public class CurrentAnswer {
    private long chatId;
    private long questionId;
    private long answerId;

    public CurrentAnswer(long questionId,long answerId,long chatId){
        this.chatId=chatId;
        this.questionId=questionId;
        this.answerId=answerId;
    }

}
