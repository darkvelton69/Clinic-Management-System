package by.darkvelton69.inputproject.exception;

import org.springframework.http.HttpStatus;
import by.darkvelton69.inputproject.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(UserAlreadyExistsException uaee){
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ERROR IS USER ALREADY EXISTS",
                uaee.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(NotFoundException nfe){
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT FOUND ERROR",
                nfe.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle (AccessDeniedException ae){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        "ERROR ACCESS",
        ae.getMessage(),
        LocalDateTime.now()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle (IllegalArgumentException iae){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "ERROR ILLEGAL",
                iae.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(BookingClosedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(BookingClosedException ex){
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "BOOKING ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }






}
