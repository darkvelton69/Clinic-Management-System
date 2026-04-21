package by.darkvelton69.inputproject.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingRequest(
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime appointmentTime
) {

}
