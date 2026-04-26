package by.darkvelton69.inputproject.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public record WorkShiftResponse(
        Long id,
        LocalDate shiftDate,
        LocalTime startTime,
        LocalTime endTime,
        DoctorResponse doctor
) {
}
