package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class RegisterCallbackHandler implements TelegramCallbackHandler{

    private final UserSessionManager sessionManager;

    public RegisterCallbackHandler(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String getCallbackData() {
        return "BTN_REGISTER";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();

        sessionManager.setUserState(chatId, BotState.WAITING_FOR_PHONE);

        return new SendMessage(String.valueOf(chatId), "Введите ваш номер телефона для привязки медицинской карты:");
    }
}
