package org.example;

//import org.example.dto.Bank;
//import org.example.dto.Currency;
//import org.example.service.CurrencyInfo;
import org.example.telegrambot.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;
import java.util.Map;

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



    public static void main(String[] args){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}