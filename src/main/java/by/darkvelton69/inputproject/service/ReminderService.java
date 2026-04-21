package by.darkvelton69.inputproject.service;


import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Condition;
import by.darkvelton69.inputproject.repository.BookingRepository;
import by.darkvelton69.inputproject.tgBot.bot.ClinicTelegramBot;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReminderService {

    private final BookingRepository bookingRepository;
    private final ClinicTelegramBot clinicTelegramBot;

    public ReminderService(BookingRepository bookingRepository, @Lazy ClinicTelegramBot clinicTelegramBot) {
        this.bookingRepository = bookingRepository;
        this.clinicTelegramBot = clinicTelegramBot;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendReminders(){
        LocalDate today = LocalDate.now();

        LocalTime targetTime = LocalTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES);

        System.out.println("DEBUG: Scheduler run at "+today+". Target time: "+targetTime);

        List<Booking> upcomingBookings = bookingRepository
                .findByAppointmentDateAndAppointmentTimeAndCondition(
                        today,
                        targetTime,
                        Condition.ACTIVE
                );

        for(Booking bkg : upcomingBookings){
            String text = String.format(
                        "*Напоминание!*\n\n"+
                        "Через час (%s) у вас запланирован прием у врача:\n %s (%s)",
                    bkg.getAppointmentTime(),
                    bkg.getDoctor().getUser().getFirstName(),
                    bkg.getDoctor().getJobTitle()

            );

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(bkg.getClient().getTelegramChatId()));
            message.setText(text);
            message.setParseMode("Markdown");

            try{
                clinicTelegramBot.execute(message);
            } catch (TelegramApiException e) {
                System.err.println("Не удалось отправить напоминание: "+e.getMessage());
            }
        }



    }


}
