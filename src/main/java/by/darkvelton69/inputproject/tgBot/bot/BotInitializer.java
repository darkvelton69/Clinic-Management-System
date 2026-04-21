package by.darkvelton69.inputproject.tgBot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {
    private final ClinicTelegramBot clinicTelegramBot;

    public BotInitializer(ClinicTelegramBot clinicTelegramBot){
        this.clinicTelegramBot = clinicTelegramBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException{
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            telegramBotsApi.registerBot(clinicTelegramBot);

            log.info("Telegram бот успешно зарегистрирован: @{}", clinicTelegramBot.getBotUsername());
        }catch (TelegramApiException e){
            log.error("Критическая ошибка при регистрации Telegram бота: {}", e.getMessage(), e);

            throw e;
        }
    }
}
