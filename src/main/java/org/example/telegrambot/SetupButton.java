package org.example.telegrambot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SetupButton {
    List<KeyboardRow> keyboardRowList = new ArrayList<>();
    private final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRowList);
    private void setupButton(){
        KeyboardRow row = new KeyboardRow();
        row.add("9");
        row.add("10");
        row.add("11");
        KeyboardRow row1 = new KeyboardRow();
        row1.add("12");
        row1.add("13");
        row1.add("14");
        KeyboardRow row2 = new KeyboardRow();
        row2.add("15");
        row2.add("16");
        row2.add("17");
        KeyboardRow row3 = new KeyboardRow();
        row3.add("18");
        row3.add("Off notification");
        keyboardRowList.add(row);
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        keyboardRowList.add(row3);
        keyboardMarkup.setKeyboard(keyboardRowList);
    }
    public ReplyKeyboardMarkup getKeyboardMarkup() {
        setupButton();
        return keyboardMarkup;
    }
    public ReplyKeyboardRemove removeKeyboard() {
        return new ReplyKeyboardRemove(true);
    }
}