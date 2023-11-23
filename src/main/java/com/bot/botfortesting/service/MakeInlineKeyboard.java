package com.bot.botfortesting.service;

import com.bot.botfortesting.model.Answer;
import com.bot.botfortesting.model.Question;
import com.bot.botfortesting.model.University;
import com.bot.botfortesting.repository.AnswerRepository;
import com.bot.botfortesting.repository.StudentRepository;
import com.bot.botfortesting.repository.UniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MakeInlineKeyboard {


    List<CurrentAnswer> currentAnswers=new ArrayList<>();

    public InlineKeyboardMarkup SingInUpKeyboard(){

        InlineKeyboardMarkup markupInLine=new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLIne=new ArrayList<>();

        List<InlineKeyboardButton> rowInLine2=new ArrayList<>();



        var regButton=new InlineKeyboardButton();
        regButton.setText("Регистрация");
        regButton.setCallbackData("REG_BUTTON");


        rowInLine2.add(regButton);


        rowsInLIne.add(rowInLine2);

        markupInLine.setKeyboard(rowsInLIne);
        return markupInLine;
    }

    public InlineKeyboardMarkup universityKeyboard(int universityPage, UniversityRepository universityRepository)
    {
        List<University> universities=universityRepository.findAll();
        long fullPage = (int)universityRepository.count()/5;
        int notFullPage=(int)(universityRepository.count()%5);
        log.info("fullPage "+fullPage+";   notFullPage "+notFullPage+" ;");

        InlineKeyboardMarkup markupInLine=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLIne=new ArrayList<>();


        if (universityPage<fullPage)
        {
            for(int i=(universityPage)*5;i<(universityPage)*5+5;i++)
            {
                makeUniversityRowsKeyboard(universities, rowsInLIne, i);

            }
        }
        else if (universityPage==fullPage)
        {
            for(int i = (universityPage)*5; i<(universityPage)* 5 + notFullPage ; i++)
            {
                makeUniversityRowsKeyboard(universities, rowsInLIne, i);
            }
        }
        List<InlineKeyboardButton> row = new ArrayList<>();

        var left=new InlineKeyboardButton();
        left.setText("<-");
        left.setCallbackData("GOLEFTPAGE");

        var right=new InlineKeyboardButton();
        right.setText("->");
        right.setCallbackData("GORIGHTPAGE");

        row.add(left);
        row.add(right);
        rowsInLIne.add(row);
        markupInLine.setKeyboard(rowsInLIne);
        return markupInLine;
    }

    private void makeUniversityRowsKeyboard(List<University> universities, List<List<InlineKeyboardButton>> rowsInLIne, int i) {
        var tempButton=new InlineKeyboardButton();
        tempButton.setText(universities.get(i).getName());
        tempButton.setCallbackData("UNIV"+universities.get(i).getName());
        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(tempButton);
        rowsInLIne.add(row);
    }
    
    public List<List<InlineKeyboardButton>> SingleChoiceKeyboard(Question question, AnswerRepository answerRepository,long chatId)
    {


        List<Answer> answers=answerRepository.findAnswerByQuestionId(question.getId());
        List<List<InlineKeyboardButton>> rowsInLIne=new ArrayList<>();

        for (Answer answer:answers) {
            var tempButton=new InlineKeyboardButton();

            tempButton.setText(answer.getName());
            tempButton.setCallbackData("SCQ"+answer.getName());
            for (CurrentAnswer currentAnswer: currentAnswers) {
                if(question.getId()==currentAnswer.getQuestionId() && answer.getId()==currentAnswer.getAnswerId() && currentAnswer.getChatId()==chatId){
                    tempButton.setText(answer.getName()+"✅");
                    tempButton.setCallbackData("SCQ"+answer.getName()+"✅");
                    break;
                }
            }


            

            List<InlineKeyboardButton> row = new ArrayList<>();


            row.add(tempButton);
            rowsInLIne.add(row);
        }


        return rowsInLIne;
    }
    public List<List<InlineKeyboardButton>> MultipleChoiceKeyboard(Question question, AnswerRepository answerRepository,long chatId)
    {


        List<Answer> answers=answerRepository.findAnswerByQuestionId(question.getId());


        List<List<InlineKeyboardButton>> rowsInLIne=new ArrayList<>();

        for (Answer answer:answers) {
            var tempButton=new InlineKeyboardButton();

            tempButton.setText(answer.getName());
            for (CurrentAnswer currentAnswer: currentAnswers) {
                if(question.getId()==currentAnswer.getQuestionId() && answer.getId()==currentAnswer.getAnswerId()&& currentAnswer.getChatId()==chatId){
                    tempButton.setText(answer.getName()+"✅");

                }
            }
            tempButton.setText(answer.getName());
            tempButton.setCallbackData("MHQ"+answer.getName());
            List<InlineKeyboardButton> row = new ArrayList<>();


            row.add(tempButton);
            rowsInLIne.add(row);
        }
        



        return rowsInLIne;
    }
    public InlineKeyboardMarkup QuestionKeyboardMarkup(Question question, AnswerRepository answerRepository,long chatId){
        List<Answer> answers=answerRepository.findAnswerByQuestionId(question.getId());



        InlineKeyboardMarkup markupInLine=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLIne;

        switch (question.getType()){
         case "SingleChoice"->{
             rowsInLIne=SingleChoiceKeyboard(question,answerRepository,chatId);
         }
         case "MultiplyChoice"->{
             rowsInLIne=MultipleChoiceKeyboard(question,answerRepository,chatId);
         }
            default -> {
                rowsInLIne=new ArrayList<>();
            }
        }

        List<InlineKeyboardButton> row = new ArrayList<>();

        var left=new InlineKeyboardButton();
        left.setText("<-");
        left.setCallbackData("GOLEFTPAGETEST");

        var right=new InlineKeyboardButton();
        right.setText("->");
        right.setCallbackData("GORIGHTPAGETEST");

        row.add(left);
        row.add(right);
        rowsInLIne.add(row);
        markupInLine.setKeyboard(rowsInLIne);

        return markupInLine;
    }

}

