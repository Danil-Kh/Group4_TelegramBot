package SendingNotifications;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class testBot extends TelegramLongPollingBot {
    private  final SendMessage message = new SendMessage();
    private final SetupButton setupButton = new SetupButton();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private final Stack<Long> stack = new Stack<>();

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String userText = update.getMessage().getText();
        sendMessages(chatId, userText);

    }
    private void sendMessages(String chatId, String userText){
        message.setChatId(chatId);

        message.setReplyMarkup(setupButton.getKeyboardMarkup());
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
                message.setText("The notification will arrive at " + userText);
                sendMessagesByTime(chatId, LocalTime.of(Integer.parseInt(userText), 00));
                break;
            case "Off notification":
                message.setText("Notifications disabled");
                service.close();
                break;
            default:
                message.setText("Click on the button");
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
    private void sendMessagesByTime(String chatId, LocalTime time) {
        LocalTime now = LocalTime.now();
        stack.push(now.until(time, ChronoUnit.SECONDS));

        if (stack.size() > 1)
            service.close();

        if(service.isShutdown())
            service = Executors.newSingleThreadScheduledExecutor();

        if (stack.peek() < 0)
            stack.set(stack.size() - 1, TimeUnit.DAYS.toSeconds(1));

        service.scheduleAtFixedRate(() -> {
                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText("off 1 sec");
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        System.out.println("e.getMessage() = " + e.getMessage());
                    }
                }, stack.peek(),
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS
        );
    }
    @Override
    public String getBotUsername() {
        return "https://t.me/testJava10bot";
    }
    @Override
    public String getBotToken(){
        return "7329214278:AAGsugx-e9kDd9gwjwpENX4DTEzW3haQSbY";
    }
}
