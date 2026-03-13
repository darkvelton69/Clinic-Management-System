package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.DoctorRegistration;
import by.darkvelton69.inputproject.dto.DoctorResponse;
import by.darkvelton69.inputproject.entity.Doctor;
import by.darkvelton69.inputproject.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/polyclinic34/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse createdDoc(@RequestBody DoctorRegistration doctorRegistration){
        return doctorService.registerDoc(doctorRegistration);
    }

    @GetMapping("/{id}")
    public DoctorResponse getDoc(@PathVariable Long id){
        return doctorService.getDoc(id);
    }


}
