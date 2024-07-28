package org.example;

import org.example.dto.Bank;
import org.example.dto.Currency;
import org.example.service.CurrencyInfo;
import org.example.telegrambot.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        CurrencyInfo currencyInfo = new CurrencyInfo();
        ArrayList <Currency> arrayCurrency = new ArrayList<>();
        arrayCurrency.add(Currency.EUR);
        arrayCurrency.add(Currency.USD);
        String result ="";

        System.out.println("=========================     NBU   ========================");
        result = currencyInfo.getExchangeRates(Bank.NBU, arrayCurrency,4);
        System.out.println(result);

        System.out.println("=========================     Privat    ========================");
        result ="";
        result = currencyInfo.getExchangeRates(Bank.PRIVATBANK, arrayCurrency,4);
        System.out.println(result);

        System.out.println("=========================    Monobank    ========================");
        result ="";
        result = currencyInfo.getExchangeRates(Bank.MONOBANK, arrayCurrency,4);
        System.out.println(result);

    }




//    public static void main(String[] args){
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new TelegramBot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
}