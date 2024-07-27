package org.example.telegrambot;

import org.example.dto.Currency;
import org.example.dto.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.example.telegrambot.TelegramBotUtils.createMessage;
import static org.example.telegrambot.TelegramBotUtils.getChatId;

public class TelegramBot extends TelegramLongPollingBot {
    private final SendMessage messageReply = new SendMessage();
    private final SetupButton setupButton = new SetupButton();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private boolean notificationsEnabled = false;
    private final User user = new User();

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = getChatId(update);
        if(User.getChatDataMap() == null){
            user.createNewChatData(chatId);
        }

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessagee(chatId, "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют",
                    Map.of("Отримати інформацію", "info_btn",
                            "Налаштування", "settings_btn")
            );
            if(messageReply.getReplyMarkup() != null){
                messageReply.setReplyMarkup(setupButton.removeKeyboard());
                try {
                    execute(messageReply);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            switch (callbackData) {
                case "settings_btn" -> {
                    SendMessagee(chatId, "Налаштування",
                            Map.of("Банк", "bank_btn",
                                    "Валюти", "curency_btn",
                                    "Оповіщення", "notif_btn")
                    );
                    if (messageReply.getReplyMarkup() != null) {
                        messageReply.setReplyMarkup(setupButton.removeKeyboard());
                        try {
                            execute(messageReply);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "bank_btn" -> {
                    SendMessagee(chatId, "Оберіть банк",
                            Map.of("НБУ", "nbu_btn",
                                    "ПриватБанк", "privat_btn",
                                    "Монобанк", "monobank_btn")
                    );
                    if (messageReply.getReplyMarkup() != null) {
                        messageReply.setReplyMarkup(setupButton.removeKeyboard());
                        try {
                            execute(messageReply);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "curency_btn" -> {
                    SendMessagee(chatId, "Оберіть валюти",
                            Map.of("USD", "usd_btn",
                                    "EUR", "eur_btn")
                    );
                    if (messageReply.getReplyMarkup() != null) {
                        messageReply.setReplyMarkup(setupButton.removeKeyboard());
                        try {
                            execute(messageReply);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "usd_btn" -> user.setCurrency(chatId, Currency.USD);
                case "eur_btn" -> user.setCurrency(chatId, Currency.EUR);
                case "notif_btn" -> {
                    notificationsEnabled = true;
                    sendMessages(chatId, "Please enter a time or 'Off notification'");
                }
                default -> notificationsEnabled = false;

                // Handle other button presses
            }
        }

        if (notificationsEnabled && update.hasMessage()) {
            sendMessages(chatId, update.getMessage().getText());
        }
    }

    private void sendMessages(long chatId, String userText) {
        messageReply.setChatId(String.valueOf(chatId));
        messageReply.setReplyMarkup(setupButton.getKeyboardMarkup());

        switch (userText) {
            case "9":
            case "10":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
            case "18":
                messageReply.setText("The notification will arrive at " + userText);
                sendMessagesByTime(chatId, LocalTime.of(Integer.parseInt(userText), 0));
                break;
            case "Off notification":
                messageReply.setText("Notifications disabled");
                service.close();
                break;
            default:
                messageReply.setText("Click on the button");
        }

        try {
            execute(messageReply);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendMessagesByTime(long chatId, LocalTime time) {
        LocalTime now = LocalTime.now();
        long delay = now.until(time, ChronoUnit.SECONDS);

        if (delay < 0) {
            delay += TimeUnit.DAYS.toSeconds(1);
        }
        if(service.isShutdown()) {
            service = Executors.newSingleThreadScheduledExecutor();
        }

        service.scheduleAtFixedRate(() -> {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Scheduled notification");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Error sending message: " + e.getMessage());
            }
        }, delay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    @Override
    public String getBotToken() {
        return "7138847779:AAHvBvcxW1CxC8CFGNCNeX9nXbtnX2Jq_Mo";
    }

    @Override
    public String getBotUsername() {
        return "s1lent_JavaProject_Testbot";
    }

    private void SendMessagee(long chatId, String text, Map<String, String> button) {
        /*
        if(messageReply.equals(null)) {
            System.out.println("Работает нормально");
            SendMessage message = createMessage(chatId, text, button);
            sendApiMethodAsync(message);
        }
        else {
            messageReply.setReplyMarkup(setupButton.removeKeyboard());
            try {
                execute(messageReply);
            } catch (TelegramApiException e) {
                System.out.println("Error sending message: " + e.getMessage());
            }
            System.out.println("Работает нормально");
            SendMessage message = createMessage(chatId, text, button);
            sendApiMethodAsync(message);
        }

         */
        SendMessage message = createMessage(chatId, text, button);
        sendApiMethodAsync(message);
    }
}