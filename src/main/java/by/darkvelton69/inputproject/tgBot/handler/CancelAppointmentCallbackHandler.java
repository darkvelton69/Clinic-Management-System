package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.service.BookingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CancelAppointmentCallbackHandler implements TelegramCallbackHandler{

    private final BookingService bookingService;

    public CancelAppointmentCallbackHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String getCallbackData() {
        return "CANCELBKG_";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String data =callbackQuery.getData();

        Long appointmentId = Long.parseLong(data.replace("CANCELBKG_",""));



        bookingService.cancelAppointment(appointmentId);

        String text = "Запись успешно отменена";

        SendMessage message= new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        message.setParseMode("Markdown");

        return message;
    }
}
