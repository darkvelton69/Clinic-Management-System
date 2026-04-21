package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.service.ClientService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Component
public class ProfileCallbackHandler implements TelegramCallbackHandler {

    private final ClientService clientService;

    public ProfileCallbackHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String getCallbackData() {
        return "BTN_PROFILE";
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();

        Optional<Client> clientOpt = clientService.findByTelegramChatId(chatId);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            String firstName = "Не указано";
            String lastName = "Не указано";
            String middleName = "Не указано";
            if (client.getUser() != null && client.getUser().getFirstName() != null && client.getUser().getMiddleName() != null && client.getUser().getLastName() != null) {
                firstName = client.getUser().getFirstName();
                middleName = client.getUser().getMiddleName();
                lastName = client.getUser().getLastName();
            }

            String phone = client.getPhone() != null ? client.getPhone() : "Не указан";

            Long age = client.getUser().getAge();


            message.setText("👤 Ваш профиль пациента:\n" +
                    "Имя: " + firstName + "\n" +
                    "Фамилия: " + middleName + "\n" +
                    "Отчество: " + lastName + "\n" +
                    "Возраст: " + age + " лет.\n" +
                    "Телефон: " + phone + "\n" +
                    "Медицинская карта активна.");


        } else {
            message.setText("Вы еще не привязали свой Telegram к профилю клиники. Нажмите 'Привязать номер' в главном меню.");
        }
        return message;
    }
}
