package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.DoctorRegistration;
import by.darkvelton69.inputproject.dto.DoctorResponse;
import by.darkvelton69.inputproject.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polyclinic34/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse createdDoctor(@RequestBody DoctorRegistration doctorRegistration){
        return doctorService.registerDoctor(doctorRegistration);
    }

    @GetMapping("/{id}")
    public DoctorResponse getDoctor(@PathVariable Long id){
        return doctorService.getDoctor(id);
    }

    @GetMapping("/list/{jobTitle}")
    public ResponseEntity<Page<DoctorResponse>> getDoctorList(@PathVariable String jobTitle, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(doctorService.getAllDoctorByJobTitle(jobTitle, page, size));
    }


}
