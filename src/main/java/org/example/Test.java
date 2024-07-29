package org.example;

import org.example.telegrambot.TelegramBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    /*
    public static void main(String[] args) {
        CurrencyInfo currencyInfo = new CurrencyInfo();
        Map<Currency,Double> mapCurrencies = new HashMap<>();

        System.out.println("=========================     NBU   ========================");
        String result ="";
        mapCurrencies.put(Currency.EUR, (double) 0);
        mapCurrencies.put(Currency.USD, 0D);
        result = currencyInfo.getExchangeRates(Bank.NBU, mapCurrencies,4);
        if (!result.isBlank()){System.out.println(result);}

        for (Map.Entry<Currency, Double> entry : mapCurrencies.entrySet()) {
            System.out.println("Currency: " + entry.getKey() + ", Rate: " + entry.getValue());
        }
        System.out.println("=========================     Privat    ========================");
        result ="";
        mapCurrencies.put(Currency.EUR, 0D);
        mapCurrencies.put(Currency.USD, 0D);

        result = currencyInfo.getExchangeRates(Bank.PRIVATBANK, mapCurrencies,3);
        if (!result.isBlank()){System.out.println(result);}

        for (Map.Entry<Currency, Double> entry : mapCurrencies.entrySet()) {
            System.out.println("Currency: " + entry.getKey() + ", Rate: " + entry.getValue());
        }

        System.out.println("=========================    Monobank    ========================");
        result ="";
        mapCurrencies.put(Currency.EUR, 0D);
        mapCurrencies.put(Currency.USD, 0D);

        result = currencyInfo.getExchangeRates(Bank.MONOBANK, mapCurrencies,3);
        if (!result.isBlank()){System.out.println(result);}

        for (Map.Entry<Currency, Double> entry : mapCurrencies.entrySet()) {
            System.out.println("Currency: " + entry.getKey() + ", Rate: " + entry.getValue());
        }

    }
    */
    static Logger logger = Logger.getLogger(Test.class.getName());
    public static void main(String[] args){
        String botToken = "7329214278:AAGsugx-e9kDd9gwjwpENX4DTEzW3haQSbY";
        try(TelegramBotsLongPollingApplication telegramBotsApi = new TelegramBotsLongPollingApplication()) {
            telegramBotsApi.registerBot(botToken, new TelegramBot(botToken));
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }
    }
}