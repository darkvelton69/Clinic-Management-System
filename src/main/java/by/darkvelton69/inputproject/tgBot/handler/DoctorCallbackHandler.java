package by.darkvelton69.inputproject.tgBot.handler;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorCallbackHandler implements TelegramCallbackHandler{

    private final UserSessionManager sessionManager;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM");

    public DoctorCallbackHandler(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String getCallbackData() {
        return "DOC_";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String callbackData = callbackQuery.getData();

        Long doctorId = Long.parseLong(callbackData.replace("DOC_",""));

        AppointmentDraft draft = sessionManager.getDraft(chatId);
        draft.setDoctorId(doctorId);

        sessionManager.setUserState(chatId, BotState.WAITING_FOR_DATE);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Шаг 3/4: Врач выбран.\nВыберите удобную дату для визита:");
        message.setReplyMarkup(getDateKeyboard());

        return message;
    }

    private InlineKeyboardMarkup getDateKeyboard(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for(int i = 0; i<7;i++){
            LocalDate date = today.plusDays(i);
            InlineKeyboardButton btn = new InlineKeyboardButton();

            btn.setText(date.format(DATE_FORMATTER));
            btn.setCallbackData("DATE_"+date.toString());

            rows.add(List.of(btn));

        }

        markup.setKeyboard(rows);
        return markup;
    }
}
