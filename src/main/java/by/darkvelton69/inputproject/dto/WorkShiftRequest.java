package by.darkvelton69.inputproject.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record WorkShiftRequest(
        Long doctorId,
        LocalDate shiftDate,
        LocalTime startTime,
        LocalTime endTime
) {
}
