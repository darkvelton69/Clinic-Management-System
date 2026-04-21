package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RegisterCommandHandler implements TelegramCommandHandler {

    private final UserSessionManager sessionManager;

    public RegisterCommandHandler(UserSessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    @Override
    public String getCommand() {
        return "/register";
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        long chatId = update.getMessage().getChatId();

        sessionManager.setUserState(chatId, BotState.WAITING_FOR_PHONE);

        return new SendMessage(String.valueOf(chatId), "Введите ваш номер телефона для привязки медицинской карты:");
    }
}
