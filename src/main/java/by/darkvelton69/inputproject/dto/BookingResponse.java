package by.darkvelton69.inputproject.dto;

import by.darkvelton69.inputproject.entity.Condition;

import java.time.LocalDate;

public record BookingResponse(
        Long id,
        Long clientId,
        Condition condition,
        LocalDate createdAt,
        DoctorResponse doctor
) {
}
