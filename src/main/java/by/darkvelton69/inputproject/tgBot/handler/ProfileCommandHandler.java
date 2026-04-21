package by.darkvelton69.inputproject.tgBot.handler;


import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.service.ClientService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class ProfileCommandHandler implements TelegramCommandHandler {

    private final ClientService clientService;


    public ProfileCommandHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String getCommand() {
        return "/profile";
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        long chatId = update.getMessage().getChatId();

        Optional<Client> clientOpt = clientService.findByTelegramChatId(chatId);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            message.setText("👤 Ваш профиль пациента:\n" +
                    "Имя: " + client.getUser().getFirstName() + "\n" +
                    "фамилия: " + client.getUser().getMiddleName() + "\n" +
                    "Отчество: " + client.getUser().getLastName() + "\n" +
                    "Возраст: " + client.getUser().getAge() + "\n" +
                    "Телефон: " + client.getPhone() + "\n" +
                    "Медицинская карта активна.");
        } else {
            message.setText("Вы еще не привязали свой Telegram к профилю клиники. Пожалуйста, пройдите регистрацию.");
        }

        return message;
    }
}
