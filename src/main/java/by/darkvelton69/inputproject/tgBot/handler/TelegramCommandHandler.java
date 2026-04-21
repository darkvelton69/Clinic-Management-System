package by.darkvelton69.inputproject.tgBot.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramCommandHandler {

    String getCommand();

    BotApiMethod<?> handle(Update update);
}
