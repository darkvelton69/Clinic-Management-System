package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Condition;
import by.darkvelton69.inputproject.repository.BookingRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyBookingCallbackHandler implements TelegramCallbackHandler {

    private final BookingRepository bookingRepository;

    public MyBookingCallbackHandler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String getCallbackData() {
        return "BTN_MY_APPOINTMENTS";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();

        List<Booking> activeAppointments = bookingRepository
                .findByClient_TelegramChatIdAndConditionOrderByAppointmentDateAscAppointmentTimeAsc(
                        chatId,
                        Condition.ACTIVE
                );

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        if (activeAppointments.isEmpty()) {
            message.setText("У вас не активных записей к врачу.");
            return message;
        }

        message.setText("*Ваши предстоящие визиты:*\nВыберите запись для просмотра подробностей или отмены:");
        message.setParseMode("Markdown");
        message.setReplyMarkup(getAppointmentsKeyboard(activeAppointments));

        return message;
    }

    private InlineKeyboardMarkup getAppointmentsKeyboard(List<Booking> bookings) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Booking bkg : bookings) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            String btnText = String.format("%s в %s", bkg.getAppointmentDate(), bkg.getAppointmentTime());

            btn.setText(btnText);

            btn.setCallbackData("MYBKG_" + bkg.getId());

            rows.add(List.of(btn));
        }

        markup.setKeyboard(rows);
        return markup;
    }
}
