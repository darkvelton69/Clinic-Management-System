package by.darkvelton69.inputproject.dto;

import by.darkvelton69.inputproject.entity.Condition;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingResponse(
        Long id,
        Long clientId,
        Condition condition,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        DoctorResponse doctor
) {
}
