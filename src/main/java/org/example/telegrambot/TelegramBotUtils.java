package org.example.telegrambot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelegramBotUtils {
    public static long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return 0;
    }

    public static SendMessage createMessage(long chatId, String text) {
        return new SendMessage(String.valueOf(chatId), new String(text.getBytes(), StandardCharsets.UTF_8));
    }

    public static SendMessage createMessage(Long chatId, String text, Map<String, String> buttons) {
        SendMessage message = createMessage(chatId, text);
        if (buttons != null && !buttons.isEmpty())
            attachButtons(message, buttons);
        return message;
    }

    private static void attachButtons(SendMessage message, Map<String, String> buttons) {
        List<InlineKeyboardRow> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);
            keyboard.add(new InlineKeyboardRow(button));
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboard);
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}
