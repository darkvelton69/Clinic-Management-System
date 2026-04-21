package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.repository.BookingRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class AppointmentDetailCallbackHandler implements TelegramCallbackHandler {

    private final BookingRepository bookingRepository;

    public AppointmentDetailCallbackHandler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String getCallbackData() {
        return "MYBKG_";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        Long appointmentId = Long.parseLong(data.replace("MYBKG_", ""));

        Booking bkg = bookingRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Запись не найдена"));

        String text = String.format(
                "*Детали записи №%d*\n\n" +
                        "Врач: %s\n" +
                        "Специализация: %s\n" +
                        "Дата: %s\n" +
                        "Время: %s\n" +
                        "Статус: %s",
                bkg.getId(),
                bkg.getDoctor().getUser().getFirstName() + " " + bkg.getDoctor().getUser().getMiddleName() + " " + bkg.getDoctor().getUser().getLastName(),
                bkg.getDoctor().getJobTitle(),
                bkg.getAppointmentDate(),
                bkg.getAppointmentTime(),
                bkg.getCondition()
        );

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode("Markdown");
        message.setReplyMarkup(getDetailKeyboard(bkg.getId()));

        return message;
    }

    private InlineKeyboardMarkup getDetailKeyboard(Long bkgId){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton cancelBtn = new InlineKeyboardButton();
        cancelBtn.setText("Отменить эту запись");
        cancelBtn.setCallbackData("CANCELBKG_"+bkgId);

        InlineKeyboardButton backBtn = new InlineKeyboardButton();
        backBtn.setText("Назад к списку");
        backBtn.setCallbackData("BTN_MY_APPOINTMENTS");

        markup.setKeyboard(List.of(List.of(cancelBtn), List.of(backBtn)));
        return markup;
    }
}
