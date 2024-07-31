package org.example.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class User {
    Logger logger = Logger.getLogger(User.class.getName());
    private static final String FILE_NAME = "src/main/resources/chat_data.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Map<Long, ChatData> chatDataMap;

    public User() {
        //Метод LoadFromFile, який отримує інфу з файлу json та дозволяє змінювати інфу надалі.
        loadFromFile();
    }
    //запис інфи про назву валюти та подальше її збереження до файлу json
    public void setCurrency(long chatId, Currency currency) {
        //реалізація логіки для валют: видалення при наявності в БД + повторному натисканні кнопки, інакше - додати валюту
        if (chatDataMap.get(chatId).currency.size() > 1) {
            if(chatDataMap.get(chatId).currency.contains(currency))
                chatDataMap.get(chatId).currency.remove(currency);
            else chatDataMap.get(chatId).currency.add(currency);
        }
        else {
            if(!chatDataMap.get(chatId).currency.contains(currency))
                chatDataMap.get(chatId).currency.add(currency);
        }
        saveToFile();
    }
    public List<Currency> getCurrency(long chatId) {
        return chatDataMap.get(chatId).currency;
    }
    public int getDecimalPlaces(long chatId) {
        return chatDataMap.get(chatId).decimalPlaces;
    }
    public Bank getBankName(long chatId) {
        return chatDataMap.get(chatId).bankName;
    }


    //запис інфи про к-сть знаків після коми та подальше її збереження до файлу json
    public void setDecimalPlaces(long chatId, int decimalPlaces) {
        chatDataMap.get(chatId).decimalPlaces = decimalPlaces;
        saveToFile();
    }
    //запис інфи про назву банку та подальше її збереження до файлу json
    public void setBankName(long chatId, Bank bankName) {
        chatDataMap.get(chatId).bankName = bankName;
        saveToFile();
    }
    public void setNotificationTime(long chatId, int notificationTime) {
        chatDataMap.get(chatId).notificationTime = notificationTime;
        saveToFile();
    }

    //створення нової комірки ChatData, якщо користувач новий/відсутній у БД
    public void createNewChatData(long chatId){
        List<Currency> defaultCurrency = new ArrayList<>();
        defaultCurrency.add(Currency.EUR);
        chatDataMap.putIfAbsent(chatId, new ChatData(defaultCurrency, 2, Bank.NBU, -1));
    }
    //Отримання мапи(інфи з ДБ) для подальшої її зміни та запису.
    private void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<Map<Long, ChatData>>(){}.getType();
            chatDataMap = gson.fromJson(reader, type);
            if(chatDataMap == null)
                chatDataMap = new HashMap<>();
        } catch (IOException e) {
            chatDataMap = new HashMap<>();
        }
    }
    //Перезапис мапи до файлу json
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(chatDataMap, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO Exception occurred", e);
        }
    }


}