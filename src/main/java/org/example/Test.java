package org.example;

import org.example.telegrambot.TelegramBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.Properties;
import java.util.logging.*;

public class Test {
    static Logger logger = Logger.getLogger(Test.class.getName());
    public static void main(String[] args){

        String botToken = "7138847779:AAHvBvcxW1CxC8CFGNCNeX9nXbtnX2Jq_Mo";
        try(TelegramBotsLongPollingApplication telegramBotsApi = new TelegramBotsLongPollingApplication()) {
            telegramBotsApi.registerBot(botToken, new TelegramBot(botToken));
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }
    }
}