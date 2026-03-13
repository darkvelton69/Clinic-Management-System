package by.darkvelton69.inputproject.dto;

import java.time.LocalDateTime;

public record PassportResponse(
        Long id,
        String series,
        String number,
        String issuedBy,
        LocalDateTime issuedDate
) {
}
