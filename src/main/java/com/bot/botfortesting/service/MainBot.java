package com.bot.botfortesting.service;


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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Component
public class MainBot extends TelegramLongPollingBot{

    final BotConfig config;

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
            }
        }
    }

    private void StartBot(long chatId) {

        Student student=new Student("svp.20@uni-dubna.ru","11223344");
        studentRepository.save(student);

        for(long i=1;i<studentRepository.count()+1;i++)
        {
            if(studentRepository.findById(i).isPresent())
                System.out.println(i+"  "+studentRepository.findById(i).get().getName());
        }

        nullKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Привет");
        ReplyKeyboardRemove keyboardMarkup = nullKeyboard();
        sendMessage.setReplyMarkup(keyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void Testing(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите действие:");
        ReplyKeyboardMarkup keyboardMarkup = createKeyboard();
        sendMessage.setReplyMarkup(keyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboardRemove nullKeyboard() {

        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();

        keyboardMarkup.setRemoveKeyboard(true);



        return keyboardMarkup;
    }
    private ReplyKeyboardMarkup createKeyboard() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Кнопка 1"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Кнопка 2"));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("Кнопка 3"));

        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("Кнопка 4"));

        keyboardMarkup.setKeyboard(Arrays.asList(row1, row2,row3,row4));

        return keyboardMarkup;
    }
}
