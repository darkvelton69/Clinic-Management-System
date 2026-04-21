package by.darkvelton69.inputproject.tgBot.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface TelegramCallbackHandler {

    String getCallbackData();

    BotApiMethod<?> handle(CallbackQuery callbackQuery);
}
