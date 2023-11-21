package com.bot.botfortesting.service;


import com.bot.botfortesting.OperationStatus;
import com.bot.botfortesting.config.BotConfig;
import com.bot.botfortesting.model.*;
import com.bot.botfortesting.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.*;

@Slf4j
@Component
public class MainBot extends TelegramLongPollingBot{

    final BotConfig config;
    private OperationStatus operationStatus=OperationStatus.NoAuthorized;
    String currentName;
    String currentGroup;
    University currentUniversity;
    int universityPage=1;
    int testPage=0;
    List<Question> questionsInTest=new ArrayList<>();
    MakeInlineKeyboard makeInlineKeyboard=new MakeInlineKeyboard();

    //Репозитроии
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestsToGroupsRepository testsToGroupsRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private QuestionsToGroupsRepository questionsToGroupsRepository;


    public MainBot(BotConfig config){

        this.config=config;

        List<BotCommand> listOfCommands=new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","Старт"));
        listOfCommands.add(new BotCommand("/test","Тестирование"));
        try {
            this.execute(new SetMyCommands(listOfCommands,new BotCommandScopeDefault(),null));
        }
        catch (TelegramApiException e) {
            log.error("Error set command : "+e.getMessage());

        }

    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> startBot(chatId);
                case "/test" -> {
                    if(operationStatus==OperationStatus.Testing){
                        simpleSend(chatId,"Завершите прошлый тест");
                        break;
                    }
                    testing(chatId,"123");
                }
                default -> lookStatus(chatId,messageText);
            }
        }
        else if(update.hasCallbackQuery()){
            String callbackData=update.getCallbackQuery().getData();
            long chatId=update.getCallbackQuery().getMessage().getChatId();
            int messageId=update.getCallbackQuery().getMessage().getMessageId();
            String messageText=update.getCallbackQuery().getMessage().getText();
            if(callbackData.startsWith("UNIV")){
                String parameters = callbackData.substring("UNIV".length());
                currentUniversity= universityRepository.findUniversitiesByName(parameters);
                studentRepository.save(new Student(currentName,currentGroup,chatId,currentUniversity.getId()));
                operationStatus=OperationStatus.Authorized;
                deletInlineKeyboard(chatId,messageId,messageText);
                simpleSend(chatId,"Вы авторизовались, как "+currentName);
            }
            if(callbackData.startsWith("SCQ")){
                String parameters = callbackData.substring("SCQ".length());
                if(callbackData.endsWith("✅"))
                {
                    String endParameters= parameters.substring(0, parameters.length() - 1);
                    Answer answer=answerRepository.getAnswerByNameAndQuestionId(endParameters,questionsInTest.get(testPage).getId());
                    makeInlineKeyboard.currentAnswers.remove(new CurrentAnswer(questionsInTest.get(testPage).getId(),answer.getId()));
                    changePageQuestion(chatId, messageId);
                    return;
                }

                Answer answer=answerRepository.getAnswerByNameAndQuestionId(parameters,questionsInTest.get(testPage).getId());
                makeInlineKeyboard.currentAnswers.add(new CurrentAnswer(questionsInTest.get(testPage).getId(),answer.getId()));
                changePageQuestion(chatId, messageId);
            }
            switch (callbackData) {


                case  "REG_BUTTON" -> {
                    operationStatus = OperationStatus.NeedNameReg;
                    lookStatus(chatId, messageText);
                }
                case  "GOLEFTPAGE"-> {
                    if (universityPage > 1) {
                        universityPage -= 1;
                        changePage(chatId, messageId);
                    }
                    else {
                        log.info("Переход на несуществующею страницу");
                    }


                }

                case "GORIGHTPAGE"-> {

                    long fullPage = (int) universityRepository.count() / 5;

                    if (universityPage <= fullPage) {
                        universityPage += 1;
                        changePage(chatId, messageId);
                    }
                    else {
                        log.info("Переход на несуществующею страницу");
                    }

                }
                case  "GOLEFTPAGETEST"-> {
                    log.info(questionsInTest.toString());

                    if (testPage > 0) {
                        testPage -= 1;
                        log.info("НОМЕР СТРАНИЦЫ : "+testPage);
                        changePageQuestion(chatId, messageId);
                    }
                    else {
                        log.info("Переход на несуществующею страницу");
                    }


                }

                case "GORIGHTPAGETEST"-> {
                    log.info(questionsInTest.toString());

                    long fullPage = questionsInTest.size()-1;

                    if (testPage < fullPage) {
                        testPage += 1;
                        log.info("НОМЕР СТРАНИЦЫ : "+testPage);
                        changePageQuestion(chatId, messageId);
                    }
                    else {
                        log.info("Переход на несуществующею страницу");
                    }

                }

                default -> {
                    log.info("CallbackData is "+callbackData);
                }
            }

        }
    }
    public void data(){
        Question q1=new Question("Вопрос 1","SingleChoice");
        Question q2=new Question("Вопрос 2","MultiplyChoice");
        Question q3=new Question("Вопрос 3","SingleChoice");
        questionRepository.save(q1);
        questionRepository.save(q2);
        questionRepository.save(q3);
        Group g1=new Group();
        groupRepository.save(g1);
        QuestionsToGroups qtg=new QuestionsToGroups(q1.getId(),g1.getId());
        QuestionsToGroups qtg1=new QuestionsToGroups(q2.getId(),g1.getId());
        QuestionsToGroups qtg2=new QuestionsToGroups(q2.getId(),g1.getId());
        questionsToGroupsRepository.save(qtg);
        questionsToGroupsRepository.save(qtg1);
        questionsToGroupsRepository.save(qtg2);
        Test test=new Test("123");
        testRepository.save(test);
        TestsToGroups ttg=new TestsToGroups(g1.getId(),test.getId(),2);
        testsToGroupsRepository.save(ttg);
        answerRepository.save(new Answer("Ответ1Вопрос1",true,q1.getId()));
        answerRepository.save(new Answer("Ответ2Вопрос1",false,q1.getId()));
        answerRepository.save(new Answer("Ответ3Вопрос1",false,q1.getId()));
        for(int i=0;i<13;i++)
        {
            University un=new University("Unik "+String.valueOf(i));
            universityRepository.save(un);
        }
    }

    private void changePage(long chatId, int messageId) {
        EditMessageText massage=new EditMessageText();
        massage.setChatId(chatId);
        massage.setMessageId(messageId);
        massage.setText("Выберите университет");



        massage.setReplyMarkup(makeInlineKeyboard.universityKeyboard(universityPage,universityRepository));

        try {
            execute(massage);
        } catch (TelegramApiException e) {
            log.error("Error send massage :"+ e.getMessage());
        }
    }
    private void changePageQuestion(long chatId, int messageId) {
        EditMessageText massage=new EditMessageText();
        massage.setChatId(chatId);
        massage.setMessageId(messageId);
        massage.setText(questionsInTest.get(testPage).getName());



        massage.setReplyMarkup(makeInlineKeyboard.QuestionKeyboardMarkup(questionsInTest.get(testPage),answerRepository));

        try {
            execute(massage);
        } catch (TelegramApiException e) {
            log.error("Error send massage :"+ e.getMessage());
        }
    }
    private void deletInlineKeyboard(long chatId,int messageId,String messageText){
        EditMessageText massage=new EditMessageText();
        massage.setChatId(chatId);
        massage.setMessageId(messageId);
        massage.setText(messageText);



        massage.setReplyMarkup(null);

        try {
            execute(massage);
        } catch (TelegramApiException e) {
            log.error("Error send massage :"+ e.getMessage());
        }
    }

    private void sendMsg(SendMessage sendMessage)
    {

        try {
            log.info("Sending massage : "+sendMessage.getText());
            if(sendMessage.getReplyMarkup() !=null)
                log.debug(sendMessage.getReplyMarkup().toString());
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error send massage :"+ e.getMessage());
            log.debug(sendMessage.getText()+"   "+sendMessage.getReplyMarkup().toString());
        }
    }
    private void simpleSend(long chatId,String text){
        log.info("Sending massage : "+text+" to "+ chatId);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMsg(sendMessage);

    }
    private void startBot(long chatId) {
        data();

//        Student student=new Student("eaa.20@uni-dubna.ru","11223344");
//        studentRepository.save(student);
//        Student student2=new Student("eеу.20@uni-dubna.ru","1413");
//        studentRepository.save(student2);

//        for(int i=0;i<13;i++)
//        {
//            University un=new University("Unik "+String.valueOf(i));
//            universityRepository.save(un);
//        }

        List<Student> students=studentRepository.findAll();
        for (Student st:students) {

            if(st.getChatId()==chatId)
            {
                log.info("Login by Telegram Id for : "+chatId);

                simpleSend(chatId,"Вы авторизовались, как "+st.getName());
                return;
            }
        }


        signInUp(chatId);


    }
    private void testing(long chatId,String testName) {
        operationStatus=OperationStatus.Testing;
        Test test=testRepository.findByName(testName);
        if(test != null)
        {
            log.info("найден тест: "+test.getName());
            goTest(chatId,test);
            SendMessage sendMessage=new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(questionsInTest.get(0).getName());
            sendMessage.setReplyMarkup(makeInlineKeyboard.QuestionKeyboardMarkup(questionsInTest.get(0),answerRepository));
            sendMsg(sendMessage);
        }
        else simpleSend(chatId,"Данного теста не существует");


    }
    private  void goTest(long chatId,Test test){

        List<TestsToGroups> testsToGroups= testsToGroupsRepository.findAllByTestId(test.getId());
        for (TestsToGroups teToGgr: testsToGroups) {
            if(groupRepository.findById(teToGgr.getGroupId()).isPresent()) {
                Group groupsThisTest = groupRepository.findById(teToGgr.getGroupId()).get();
                List<QuestionsToGroups> questionsToGroups=questionsToGroupsRepository.findAllByGroupId(groupsThisTest.getId());
                List<Long> ids= questionsToGroups.stream().map(QuestionsToGroups::getQuestionId).toList();
                log.info("Id question"+ ids.toString());
                List<Question> questions=questionRepository.findAllById(ids);
                shuffle(questions);
                for (int i=0;i<teToGgr.getQuestionsCount();i++){
                    Question qs=questions.get(i);
                    questionsInTest.add(qs);
                }
                log.info(questionsInTest.toString());
            }
            else{
                log.info("Нет группы для теста: "+teToGgr.getId());
            }
        }






    }
    private void signInUp(long chatId){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Зарегистрироваться?");
        sendMessage.setReplyMarkup(makeInlineKeyboard.SingInUpKeyboard());
        sendMsg(sendMessage);

    }



    private void lookStatus(long chatId,String messegeText) {
        switch (operationStatus){

            case NeedNameReg -> {

                simpleSend(chatId,"Введите логин/почту");
                operationStatus=OperationStatus.TakeNameReg;

            }
            case TakeUnivReg -> {
                SendMessage sendMessage=new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Выберите университе");



                sendMessage.setReplyMarkup(makeInlineKeyboard.universityKeyboard(universityPage,universityRepository));
                sendMsg(sendMessage);
            }
            case TakeNameReg -> {
                currentName=messegeText;
                List<Student> students=studentRepository.findAll();
                for (Student st:students) {
                    if(st.getName().equals(currentName)){
                        simpleSend(chatId,"Данное имя занято");
                        operationStatus=OperationStatus.NoAuthorized;
                        return;
                    }
                }

                simpleSend(chatId,"Введите группу");
                operationStatus=OperationStatus.TakeGroupReg;


            }
            case TakeGroupReg -> {
                currentGroup=messegeText;
                //studentRepository.save(new Student(currentName,currentPass,chatId));

                operationStatus=OperationStatus.TakeUnivReg;
                lookStatus(chatId,messegeText);

            }
            case NoAuthorized -> signInUp(chatId);
            default -> log.info("Status Authorized");
        }


    }


}