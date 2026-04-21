package by.darkvelton69.inputproject.tgBot.handler;


import by.darkvelton69.inputproject.tgBot.bot.BotState;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramStateHandler {

    BotState getState();

    BotApiMethod<?> handle(Update update);

}
