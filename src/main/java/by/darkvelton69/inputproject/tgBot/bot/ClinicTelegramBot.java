package by.darkvelton69.inputproject.tgBot.bot;

import by.darkvelton69.inputproject.tgBot.config.BotConfig;
import by.darkvelton69.inputproject.tgBot.handler.TelegramCallbackHandler;
import by.darkvelton69.inputproject.tgBot.handler.TelegramCommandHandler;
import by.darkvelton69.inputproject.tgBot.handler.TelegramStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ClinicTelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final Map<String, TelegramCommandHandler> commandHandlers;
    private final Map<BotState, TelegramStateHandler> stateHandlers;
    private final Map<String, TelegramCallbackHandler> callbackHandlers;
    private final UserSessionManager sessionManager;

    public ClinicTelegramBot(BotConfig botConfig,
                             List<TelegramCommandHandler> commandHandlerList,
                             List<TelegramStateHandler> stateHandlerList, Map<String, TelegramCallbackHandler> callbackHandlers,
                             UserSessionManager sessionManager, List<TelegramCallbackHandler> callbackHandlerList) {
        super(botConfig.token());
        this.botConfig = botConfig;
        this.sessionManager = sessionManager;

        this.commandHandlers = commandHandlerList.stream()
                .collect(Collectors.toMap(TelegramCommandHandler::getCommand, Function.identity()));

        this.stateHandlers = stateHandlerList.stream()
                .collect(Collectors.toMap(TelegramStateHandler::getState, Function.identity()));

        this.callbackHandlers = callbackHandlerList.stream()
                .collect(Collectors.toMap(TelegramCallbackHandler::getCallbackData, Function.identity()));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            BotState currentState = sessionManager.getUserState(chatId);
            BotApiMethod<?> response;

            if (currentState == BotState.DEFAULT) {
                TelegramCommandHandler handler = commandHandlers.get(text);

                if (handler != null) {
                    response = handler.handle(update);
                } else {
                    response = new SendMessage(String.valueOf(chatId), "Неизвестная команда. Введите /start");
                }
            } else {
                TelegramStateHandler handler = stateHandlers.get(currentState);
                if (handler != null) {
                    response = handler.handle(update);
                } else {
                    response = new SendMessage(String.valueOf(chatId), "Произошла ошибка. Возврат в главное меню.");
                    sessionManager.setUserState(chatId, BotState.DEFAULT);
                }
            }
            sendResponse(response);
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();

            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(callbackQuery.getId());
            sendResponse(answer);

            TelegramCallbackHandler handler = callbackHandlers.get(callbackData);

            if (handler == null) {
                handler = callbackHandlers.values().stream()
                        .filter(h->callbackData.startsWith(h.getCallbackData()))
                        .findFirst()
                        .orElse(null);
            }

            if (handler != null) {
                sessionManager.setUserState(chatId, BotState.DEFAULT);

                BotApiMethod<?> response = handler.handle(callbackQuery);
                sendResponse(response);
            } else {
                log.warn("Неизвестный callback data: {}", callbackData);
            }
        }
    }

    private void sendResponse(BotApiMethod<?> response) {
        if (response != null) {
            try {
                execute(response);
            } catch (TelegramApiException e) {
                log.error("Ошибка при отправке ответа: {}", e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.name();
    }
}
