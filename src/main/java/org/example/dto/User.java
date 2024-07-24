package org.example.dto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Getter
public class User {
    private static final String FILE_NAME = "src/main/java/org/example/dto/chat_data.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Map<Long, ChatData> chatDataMap;
    private static final ArrayList<Currency> defaultCurrency = new ArrayList<>();
    //внутрішній клас, свого роду нода, тип даних для позначення того, що до мапи з ключем chatId належить об’єкт ChatData, що містить інфу про назву банку, к-сть знаків після коми та валюти, курс яких буде показуватись.

    public User() {
        //Метод LoadFromFile, який отримує інфу з файлу json та дозволяє змінювати інфу надалі.
        loadFromFile();
    }
    public ArrayList<Currency> getCurrency(Long chatId) {
        System.out.println("getCurrency works!!!");
        System.out.println(chatDataMap);
        return chatDataMap.get(chatId).currency;
    }

    //запис інфи про назву валюти та подальше її збереження до файлу json
    public void setCurrency(long chatId, ArrayList<Currency> currency) {
        getOrCreateChatData(chatId).currency = currency;
        System.out.println("SetCurrency works!!!");
        saveToFile();
    }
    //запис інфи про к-сть знаків після коми та подальше її збереження до файлу json
    public void setDecimalPlaces(long chatId, int decimalPlaces) {
        getOrCreateChatData(chatId).decimalPlaces = decimalPlaces;
        saveToFile();
    }
    //запис інфи про назву банку та подальше її збереження до файлу json
    public void setBankName(long chatId, Bank bankName) {
        getOrCreateChatData(chatId).bankName = bankName;
        saveToFile();
    }

    //створення нової комірки ChatData, якщо користувач новий/відсутній у файлі, інакше - повернути дану комірку.
    private ChatData getOrCreateChatData(long chatId) {
        defaultCurrency.add(Currency.UAH);
        return chatDataMap.computeIfAbsent(chatId, k -> new ChatData(defaultCurrency, 0, Bank.NBU));
    }
    //Отримання мапи(інфи з ДБ) для подальшої її зміни та запису.
    private void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<Map<Long, ChatData>>(){}.getType();
            chatDataMap = gson.fromJson(reader, type);
            System.out.println("Loads!!!");
            if (chatDataMap == null) {
                chatDataMap = new HashMap<>();
                System.out.println("Loads!!!");
            }
        } catch (IOException e) {
            chatDataMap = new HashMap<>();
            System.out.println("Loads!!!");
        }
    }
    //Перезапис мапи до файлу json
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(chatDataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
