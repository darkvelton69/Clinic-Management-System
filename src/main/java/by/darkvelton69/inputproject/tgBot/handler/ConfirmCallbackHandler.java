package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.service.BookingService;
import by.darkvelton69.inputproject.tgBot.bot.AppointmentDraft;
import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class ConfirmCallbackHandler implements TelegramCallbackHandler{

    private final UserSessionManager sessionManager;
    private final BookingService bookingService;

    public ConfirmCallbackHandler(UserSessionManager sessionManager, BookingService bookingService) {
        this.sessionManager = sessionManager;
        this.bookingService = bookingService;
    }


    @Override
    public String getCallbackData() {
        return "CONFIRM_";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        if(data.equals("CONFIRM_NO")){
            sessionManager.clearDraft(chatId);
            return new SendMessage(String.valueOf(chatId), "Запись отменена. Вы можете начать заново через меню.");
        }

        try {

            AppointmentDraft draft = sessionManager.getDraft(chatId);

            bookingService.createAppointmentFromDraft(chatId, draft);

            sessionManager.clearDraft(chatId);
            sessionManager.setUserState(chatId, BotState.DEFAULT);

            return new SendMessage(String.valueOf(chatId), "Поздравляем! Вы успешно записаны на прием. Напоминание придет за час до визита.");

        }catch (Exception e){
            return new SendMessage(String.valueOf(chatId), "Произошла ошибка при сохранении записи. Пожалуйста, обратитесь в регистратуру");
        }
    }
}
