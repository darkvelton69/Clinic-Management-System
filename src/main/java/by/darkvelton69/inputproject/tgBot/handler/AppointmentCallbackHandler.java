package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.service.ClientService;
import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentCallbackHandler implements TelegramCallbackHandler{

    private final ClientService clientService;
    private final UserSessionManager sessionManager;

    public AppointmentCallbackHandler(ClientService clientService, UserSessionManager sessionManager) {
        this.clientService = clientService;
        this.sessionManager = sessionManager;
    }

    @Override
    public String getCallbackData() {
        return "BTN_APPOINTMENT";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();

        Optional<Client> clientOpt = clientService.findByTelegramChatId(chatId);

        if(clientOpt.isEmpty()){
            return new SendMessage(String.valueOf(chatId), "Для записи к врачу необходимо привязать номер телефона. Выберите 'Привязать номер' в меню.");
        }

        sessionManager.clearDraft(chatId);

        sessionManager.setUserState(chatId, BotState.WAITING_FOR_SPECIALTY);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Шаг 1/4: Выберите специализацию врача:");
        message.setReplyMarkup(getSpecialtyKeyboard());

        return message;

    }

    private InlineKeyboardMarkup getSpecialtyKeyboard(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton therapistBtn = new InlineKeyboardButton();
        therapistBtn.setText("Терапевт");
        therapistBtn.setCallbackData("SPEC_THERAPIST");

        InlineKeyboardButton surgeonBtn = new InlineKeyboardButton();
        surgeonBtn.setText("Хирург");
        surgeonBtn.setCallbackData("SPEC_SURGEON");

        rows.add(List.of(therapistBtn));
        rows.add(List.of(surgeonBtn));

        markup.setKeyboard(rows);
        return markup;

    }
}
