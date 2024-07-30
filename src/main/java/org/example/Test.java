package org.example;

import org.example.telegrambot.TelegramBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

public class Test {
    static Logger logger = Logger.getLogger(Test.class.getName());
    public static void main(String[] args){
        String botToken = "";
        Properties prop = new Properties();
        try {
            prop.load(Test.class.getClassLoader().getResourceAsStream("config.properties"));
            botToken = prop.getProperty("token");
        }
        catch (IOException ex) {
            logger.log(Level.SEVERE, "IO Exception occurred", ex);
        }
        try(TelegramBotsLongPollingApplication telegramBotsApi = new TelegramBotsLongPollingApplication()) {
            telegramBotsApi.registerBot(botToken, new TelegramBot(botToken));
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }
    }
}