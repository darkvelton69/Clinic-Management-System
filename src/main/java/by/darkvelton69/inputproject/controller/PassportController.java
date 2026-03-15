package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.PassportRequest;
import by.darkvelton69.inputproject.dto.PassportResponse;
import by.darkvelton69.inputproject.entity.Passport;
import by.darkvelton69.inputproject.service.BookingService;
import by.darkvelton69.inputproject.service.PassportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polyclinic34/profile")
@RequiredArgsConstructor
public class PassportController {
    private final PassportService passportService;


    @PostMapping("/passport")
    @ResponseStatus(HttpStatus.CREATED)
    public PassportResponse addPassport(@Valid @RequestBody PassportRequest passportRequest){
        return passportService.addPassport(passportRequest);
    }

    @PatchMapping("/edit-passport")
    public ResponseEntity<PassportResponse> editPassport(@RequestBody PassportRequest passportRequest){
        return ResponseEntity.ok(passportService.editPassport(passportRequest));
    }

}
