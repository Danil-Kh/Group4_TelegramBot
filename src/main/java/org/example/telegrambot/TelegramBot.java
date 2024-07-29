package org.example.telegrambot;

import org.apache.commons.lang3.ObjectUtils;
import org.example.dto.Bank;
import org.example.dto.Currency;
import org.example.dto.User;
import org.example.service.CurrencyInfo;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.example.telegrambot.TelegramBotUtils.createMessage;
import static org.example.telegrambot.TelegramBotUtils.getChatId;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final SetupButton setupButton = new SetupButton();
    //   private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private boolean notificationsEnabled = false;
    private final User user = new User();
    private final TelegramClient telegramClient;
    private final CurrencyInfo currencyInfo = new CurrencyInfo();
    private final Map<Long, ScheduledExecutorService> scheduledNotifications = new HashMap<>();

    public TelegramBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {

        long chatId = getChatId(update);

        if (user.getChatDataMap().get(chatId) == null) {
            user.createNewChatData(chatId);
        }

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage messageReply = new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText());
            SendMessagee(chatId, "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют",
                    Map.of("Отримати інформацію", "info_btn",
                            "Налаштування", "settings_btn")
            );

        } else if (update.hasCallbackQuery()) {
            SendMessage messageReply = new SendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), update.getCallbackQuery().getData());
            String callbackData = update.getCallbackQuery().getData();
            switch (callbackData) {
                case "settings_btn" -> {
                    SendMessagee(chatId, "Налаштування",
                            Map.of("Банк", "bank_btn",
                                    "Валюти", "curency_btn",
                                    "К-сть знаків після коми", "decplace_btn",
                                    "Оповіщення", "notif_btn")
                    );
                }
                case "bank_btn" -> {
                    SendMessagee(chatId, "Оберіть банк",
                            Map.of("НБУ", "nbu_btn",
                                    "ПриватБанк", "privat_btn",
                                    "Монобанк", "monobank_btn")
                    );
                }
                case "curency_btn" -> {
                    SendMessagee(chatId, "Оберіть валюти",
                            Map.of("USD", "usd_btn",
                                    "EUR", "eur_btn")
                    );
                }
                case "decplace_btn" -> {
                    SendMessagee(chatId, "Оберіть к-сть знаків після коми",
                            Map.of("2", "2p_btn",
                                    "3", "3p_btn",
                                    "4", "4p_btn")
                    );
                }
                case "usd_btn" -> user.setCurrency(chatId, Currency.USD);
                case "eur_btn" -> user.setCurrency(chatId, Currency.EUR);
                case "privat_btn" -> user.setBankName(chatId, Bank.PRIVATBANK);
                case "monobank_btn" -> user.setBankName(chatId, Bank.MONOBANK);
                case "nbu_btn" -> user.setBankName(chatId, Bank.NBU);
                case "2p_btn" -> user.setDecimalPlaces(chatId, 2);
                case "3p_btn" -> user.setDecimalPlaces(chatId, 3);
                case "4p_btn" -> user.setDecimalPlaces(chatId, 4);
                case "notif_btn" -> {
                    notificationsEnabled = true;
                    sendMessages(chatId, "Please enter a time or 'Off notification'", messageReply);
                }
                default -> notificationsEnabled = false;
            }
        }


        if (notificationsEnabled && update.hasMessage()) {
            SendMessage messageReply = new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText());
            sendMessages(chatId, update.getMessage().getText(), messageReply);
        }
    }

    private void sendMessages(long chatId, String userText, SendMessage messageReply) {
        messageReply.setReplyMarkup(setupButton.getKeyboardMarkup());

        switch (userText) {
            case "21":
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
                user.setNotificationTime(chatId,Integer.parseInt(userText));
                sendMessagesByTime(chatId, LocalTime.of(user.getNotificationTime(chatId), 51));
                messageReply.setReplyMarkup(setupButton.removeKeyboard());
                break;
            case "Off notification":
                scheduledNotifications.get(chatId).close();
                messageReply.setText("Notifications disabled");
                messageReply.setReplyMarkup(setupButton.removeKeyboard());
                break;
            default:
                messageReply.setText("Click on the button");

        }

        try {
            telegramClient.executeAsync(messageReply);
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
        ScheduledExecutorService existingService = scheduledNotifications.get(chatId);

        if (existingService == null || existingService.isShutdown()) {
            ScheduledExecutorService newService = Executors.newSingleThreadScheduledExecutor();
            scheduledNotifications.put(chatId, newService);
            newService.scheduleAtFixedRate(() -> {
                String string = currencyInfo.getExchangeRates(user.getBankName(chatId), user.getCurrency(chatId), user.getDecimalPlaces(chatId));
                SendMessage message = new SendMessage(String.valueOf(chatId), string);
                try {
                    telegramClient.executeAsync(message);
                } catch (TelegramApiException e) {
                    System.out.println("Error sending message: " + e.getMessage());
                }
            }, delay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
        }

    }

        private void SendMessagee(long chatId, String text, Map<String, String> button) {
            SendMessage message = createMessage(chatId, text, button);
            try {
                telegramClient.executeAsync(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
}

