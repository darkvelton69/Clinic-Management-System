package by.darkvelton69.inputproject.listener;

import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
@RequiredArgsConstructor
public class BookingEventListener {

    private final EmailService emailService;
    private final SpringTemplateEngine templateEngine;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBookingCreated(BookingCreatedEvent event) throws MessagingException {
        Booking booking = event.booking();
        String to = booking.getClient().getUser().getEmail();

        String firstName = booking.getClient().getUser().getFirstName();
        LocalDate appointmentDate = booking.getAppointmentDate();
        LocalTime appointmentTime = booking.getAppointmentTime();

        String plainTextTicket = String.format(
                "--- ТАЛОН НА ПРИЕМ ---\nПациент: %s\nДата: %s\nВремя: %s\nСтатус: Подтверждено",
                firstName, appointmentDate, appointmentTime
        );




        emailService.sendHtmlMessageWithAttachment(
                to,
                "Запись подтверждена",
                firstName,
                plainTextTicket
        );
    }

}
