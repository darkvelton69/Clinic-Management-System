package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.service.AuthService;
import by.darkvelton69.inputproject.service.ClientService;
import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PhoneRegistrationStateHandler implements TelegramStateHandler {
    private final UserSessionManager sessionManager;
    private final ClientService clientService;

    public PhoneRegistrationStateHandler(UserSessionManager sessionManager, AuthService authService, ClientService clientService){
        this.sessionManager = sessionManager;
        this.clientService = clientService;
    }

    public BotState getState(){
        return BotState.WAITING_FOR_PHONE;
    }

    public BotApiMethod<?> handle(Update update){
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if(text.matches("\\+?[0-9]{10,15}")){

            boolean isLinked = clientService.linkTelegramAccount(chatId, text);

            sessionManager.setUserState(chatId, BotState.DEFAULT);

            if(isLinked) {
                return new SendMessage(String.valueOf(chatId), "✅ Спасибо! Ваш телефон " + text + "успешно сохранен. Регистрация завершена. Теперь вы можете использовать /profile");
            }else {
                return new SendMessage(String.valueOf(chatId), "❌ Пациент с таким номером телефона не найден. Пожалуйста, обратитесь в регистратуру поликлиники для заведения медицинской карты.");
            }
        }else{
            return new SendMessage(String.valueOf(chatId), "❌ Некорректный формат телефона. Пожалуйста, введите номер (например, 89991234567):");
        }
    }
}
