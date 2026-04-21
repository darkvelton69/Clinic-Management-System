package by.darkvelton69.inputproject.tgBot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartCommandHandler implements TelegramCommandHandler {
    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getChat().getFirstName();

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Здравствуйте, "+ firstName + "! Я официальный бот клиники. Вы можете записаться на прием или посмотреть свои медицинские записи. Чем могу помочь?");

        message.setReplyMarkup(getMainMenuKeyboard());

        return message;
    }

    private InlineKeyboardMarkup getMainMenuKeyboard(){
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();

        InlineKeyboardButton profileButton = new InlineKeyboardButton();
        profileButton.setText("Мой профиль");
        profileButton.setCallbackData("BTN_PROFILE");

        InlineKeyboardButton registerButton = new InlineKeyboardButton();
        registerButton.setText("Привязать номер");
        registerButton.setCallbackData("BTN_REGISTER");

        rowInLine1.add(profileButton);
        rowInLine1.add(registerButton);

        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();

        InlineKeyboardButton appointmentButton = new InlineKeyboardButton();
        appointmentButton.setText("Записаться к врачу");
        appointmentButton.setCallbackData("BTN_APPOINTMENT");

        InlineKeyboardButton myAppointmentButton = new InlineKeyboardButton();
        myAppointmentButton.setText("Мои записи");
        myAppointmentButton.setCallbackData("BTN_MY_APPOINTMENTS");

        rowInLine2.add(appointmentButton);
        rowInLine2.add(myAppointmentButton);

        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);

        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }
}
