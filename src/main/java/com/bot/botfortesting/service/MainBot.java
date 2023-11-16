package com.bot.botfortesting.service;


import com.bot.botfortesting.OperationStatus;
import com.bot.botfortesting.config.BotConfig;
import com.bot.botfortesting.model.Question;
import com.bot.botfortesting.model.Student;
import com.bot.botfortesting.model.University;
import com.bot.botfortesting.repository.StudentRepository;
import com.bot.botfortesting.repository.UniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;


import java.util.List;

@Slf4j
@Component
public class MainBot extends TelegramLongPollingBot{

    final BotConfig config;
    private OperationStatus operationStatus=OperationStatus.NoAuthorized;
    String currentName;
    String currentGroup;
    University currentUniversity;
    int universityPage=1;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityRepository universityRepository;

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
                case "/test" -> testing(chatId);
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
                simpleSend(chatId,"Вы авторизовались, как "+currentName);
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

                default -> {
                    log.info("CallbackData is "+callbackData);
                }
            }

        }
    }

    private void changePage(long chatId, int messageId) {
        EditMessageText massage=new EditMessageText();
        massage.setChatId(chatId);
        massage.setMessageId(messageId);
        massage.setText("Выберите университет");
        MakeInlineKeyboard a = new MakeInlineKeyboard();


        massage.setReplyMarkup(a.universityKeyboard(universityPage,universityRepository));
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
    private void testing(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите действие:");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new IllegalArgumentException(e);
        }
    }
    private void signInUp(long chatId){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Зарегистрироваться?");




        MakeInlineKeyboard singKeyboard=new MakeInlineKeyboard();

        sendMessage.setReplyMarkup(singKeyboard.SingInUpKeyboard());


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

                MakeInlineKeyboard a = new MakeInlineKeyboard();

                sendMessage.setReplyMarkup(a.universityKeyboard(universityPage,universityRepository));
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

    private void askSingleChoiceQ(Question question,long chatId)
    {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(question.getQuestion());

        sendMsg(sendMessage);

    }
}
