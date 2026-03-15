package by.darkvelton69.inputproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PassportRequest (
        String series,
        String number,
        String issuedBy,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate issuedDate
){
}
