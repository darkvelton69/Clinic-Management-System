package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.service.BookingService;
import by.darkvelton69.inputproject.tgBot.bot.AppointmentDraft;
import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateCallbackHandler implements TelegramCallbackHandler {

    private final UserSessionManager sessionManager;
    private final BookingService bookingService;

    public DateCallbackHandler(UserSessionManager sessionManager, BookingService bookingService) {
        this.sessionManager = sessionManager;
        this.bookingService = bookingService;
    }


    @Override
    public String getCallbackData() {
        return "DATE_";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String callbackDate = callbackQuery.getData();

        LocalDate selectedDate = LocalDate.parse(callbackDate.replace("DATE_", ""));

        AppointmentDraft draft = sessionManager.getDraft(chatId);
        draft.setDate(selectedDate);

        List<LocalTime> availableTimes = bookingService.getAvailableTimes(draft.getDoctorId(), selectedDate);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        if (availableTimes.isEmpty()) {
            message.setText("К сожалению, на " + selectedDate + " нет свободного времени. Выберите другую дату.");
            return message;
        }

        sessionManager.setUserState(chatId, BotState.WAITING_FOR_TIME);


        message.setText("Шаг 4/4: Дата выбрана (" + selectedDate + ").\nВыберите время приема:");
        message.setReplyMarkup(getTimeKeyboard(availableTimes));

        return message;
    }

    private InlineKeyboardMarkup getTimeKeyboard(List<LocalTime> availableTimes) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> currentRow = new ArrayList<>();

        for (int i = 0; i < availableTimes.size(); i++){
            LocalTime time = availableTimes.get(i);
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(time.toString());
            btn.setCallbackData("TIME_"+time.toString());
            currentRow.add(btn);

            if((i + 1) % 3 == 0){
                rows.add(new ArrayList<>(currentRow));
                currentRow.clear();
            }


        }
        if (!currentRow.isEmpty()) rows.add(currentRow);

        markup.setKeyboard(rows);
        return markup;

    }
}
