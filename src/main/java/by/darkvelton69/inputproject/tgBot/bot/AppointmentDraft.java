package by.darkvelton69.inputproject.tgBot.bot;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDraft {
    private String specialty;
    private Long doctorId;
    private LocalDate date;
    private LocalTime time;
}
