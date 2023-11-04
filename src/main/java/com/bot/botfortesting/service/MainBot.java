package com.bot.botfortesting.service;


import com.bot.botfortesting.OperationStatus;
import com.bot.botfortesting.config.BotConfig;
import com.bot.botfortesting.model.Student;
import com.bot.botfortesting.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Component
public class MainBot extends TelegramLongPollingBot{

    final BotConfig config;
    private OperationStatus operationStatus=OperationStatus.NoAuthorized;
    String currentName,currentPass;

    @Autowired
    private StudentRepository studentRepository;

    public MainBot(BotConfig config){

        this.config=config;

        List<BotCommand> listOfCommands=new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","Старт"));
        listOfCommands.add(new BotCommand("/test","Тестирование"));
        try {
            this.execute(new SetMyCommands(listOfCommands,new BotCommandScopeDefault(),null));
        }
        catch (TelegramApiException e) {
            throw new RuntimeException(e);
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
                case "/start" -> StartBot(chatId);
                case "/test" -> Testing(chatId);
                default -> lookstatus(chatId,messageText);
            }
        }
        else if(update.hasCallbackQuery()){
            String callbackData=update.getCallbackQuery().getData();
            long chatId=update.getCallbackQuery().getMessage().getChatId();
            if(callbackData.equals("AUT_BUTTON")){
                SendMessage sendMessage=new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Введите логин/почту");
                SendMsg(sendMessage);
                operationStatus=OperationStatus.TakeNameAut;
            }
            if(callbackData.equals("REG_BUTTON")){
                SendMessage sendMessage=new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Введите логин/почту");
                SendMsg(sendMessage);
                operationStatus=OperationStatus.TakeNameReg;
            }
        }
    }



    private void SendMsg(SendMessage sendMessage)
    {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void SimpleSend(long chatId,String text){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        SendMsg(sendMessage);
    }

    private void StartBot(long chatId) {

//        Student student=new Student("eaa.20@uni-dubna.ru","11223344");
//        studentRepository.save(student);
//        Student student2=new Student("eеу.20@uni-dubna.ru","1413");
//        studentRepository.save(student2);



        List<Student> Students=studentRepository.findAll();
        for (Student st:Students) {
                System.out.println(st.getId()+" "+st.getName());
            if(st.getChatId()==chatId)
            {
                System.out.println("---------");

                SimpleSend(chatId,"Вы авторизовались, как "+st.getName());
                return;
            }
        }
        //if (message.getText().equalsIgnoreCase("/start"))

        signInUp(chatId);


    }
    private void Testing(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите действие:");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void signInUp(long chatId){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Войти или зарегистрироваться?");

        InlineKeyboardMarkup markupInLine=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLIne=new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1=new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2=new ArrayList<>();

        InlineKeyboardButton  autButton=new InlineKeyboardButton();
        autButton.setText("Авторизация");
        autButton.setCallbackData("AUT_BUTTON");

        var regButton=new InlineKeyboardButton();
        regButton.setText("Регистрация");
        regButton.setCallbackData("REG_BUTTON");

        rowInLine1.add(autButton);
        rowInLine2.add(regButton);

        rowsInLIne.add(rowInLine1);
        rowsInLIne.add(rowInLine2);

        markupInLine.setKeyboard(rowsInLIne);
        sendMessage.setReplyMarkup(markupInLine);


        SendMsg(sendMessage);

    }
    private void lookstatus(long chatId,String messegeText) {
        switch (operationStatus){
            case Authorized -> {
                return;
            }

            case TakeNameAut -> {
                currentName=messegeText;
                SimpleSend(chatId,"Введите пароль");
                operationStatus=OperationStatus.TakePassAut;
                return;
            }
            case TakePassAut -> {
                currentPass=messegeText;

                List<Student> Students=studentRepository.findAll();
                for (Student st:Students) {
                    if (st.getName().equals(currentName) && st.getPass().equals(currentPass)){
                        SimpleSend(chatId,"Вы успешно авторизовались");
                        operationStatus=OperationStatus.Authorized;
                        return;
                    }
                }

                SimpleSend(chatId,"Неверный логин или пароль");
                operationStatus=OperationStatus.NoAuthorized;
                return;
            }
            case TakeNameReg -> {
                currentName=messegeText;
                List<Student> Students=studentRepository.findAll();
                for (Student st:Students) {
                    if(st.getName().equals(currentName)){
                        SimpleSend(chatId,"Данное имя занято");
                        operationStatus=OperationStatus.NoAuthorized;
                    }
                }

                SimpleSend(chatId,"Введите пароль");
                operationStatus=OperationStatus.TakePassReg;
                return;

            }
            case TakePassReg -> {
                currentPass=messegeText;
                studentRepository.save(new Student(currentName,currentPass,chatId));
                SimpleSend(chatId,"Вы успешно зарегистрировались");
                operationStatus=OperationStatus.Authorized;
            }
            case NoAuthorized -> {
                signInUp(chatId);
            }
        }


    }
}
