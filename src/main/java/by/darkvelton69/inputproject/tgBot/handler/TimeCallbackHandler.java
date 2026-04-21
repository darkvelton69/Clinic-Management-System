package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.tgBot.bot.AppointmentDraft;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalTime;
import java.util.List;

@Component
public class TimeCallbackHandler implements TelegramCallbackHandler{

    private final UserSessionManager sessionManager;

    public TimeCallbackHandler(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String getCallbackData() {
        return "TIME_";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String callbackData = callbackQuery.getData();

        LocalTime selectedTime = LocalTime.parse(callbackData.replace("TIME_",""));

        AppointmentDraft draft = sessionManager.getDraft(chatId);
        draft.setTime(selectedTime);

        String summary = String.format(
                "📋 *Проверьте данные записи:*\n\n" +
                        "👨‍⚕️ Врач ID: %d\n" +
                        "📅 Дата: %s\n" +
                        "⏰ Время: %s\n\n" +
                        "Подтверждаете запись?",
                draft.getDoctorId(), draft.getDate(), draft.getTime()
        );

        SendMessage message = new SendMessage();
        message.setText(summary);
        message.setChatId(String.valueOf(chatId));
        message.setParseMode("Markdown");
        message.setReplyMarkup(getConfirmationKeyboard());

        return message;


    }

    private InlineKeyboardMarkup getConfirmationKeyboard(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton confirmBtn = new InlineKeyboardButton();
        confirmBtn.setText("Да, подтверждаю");
        confirmBtn.setCallbackData("CONFIRM_YES");

        InlineKeyboardButton cancelBtn = new InlineKeyboardButton();
        cancelBtn.setText("Отмена");
        cancelBtn.setCallbackData("CONFIRM_NO");

        markup.setKeyboard(List.of(List.of(confirmBtn, cancelBtn)));
        return markup;

    }
}
