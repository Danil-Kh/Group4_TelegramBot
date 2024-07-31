package org.example;

import org.example.telegrambot.TelegramBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

public class AppLauncher {
    public static void main(String[] args){
        Logger logger = Logger.getLogger(AppLauncher.class.getName());
        Properties prop = new Properties();
        String botToken = "";
        try {
            prop.load(AppLauncher.class.getClassLoader().getResourceAsStream("config.properties"));
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