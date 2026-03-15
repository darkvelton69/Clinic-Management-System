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
    public ErrorResponse handle(UserAlreadyExistsException userAlreadyExistsException){
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ERROR IS USER ALREADY EXISTS",
                userAlreadyExistsException.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(RoleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(RoleException roleException){
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "ERROR IS ROLE",
                roleException.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(NotFoundException notFoundException){
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT FOUND ERROR",
                notFoundException.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle (AccessDeniedException accessDeniedException){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        "ERROR ACCESS",
        accessDeniedException.getMessage(),
        LocalDateTime.now()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle (IllegalArgumentException illegalArgumentException){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "ERROR ILLEGAL",
                illegalArgumentException.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(BookingClosedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(BookingClosedException bookingClosedException){
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "BOOKING ERROR",
                bookingClosedException.getMessage(),
                LocalDateTime.now()
        );
    }






}
