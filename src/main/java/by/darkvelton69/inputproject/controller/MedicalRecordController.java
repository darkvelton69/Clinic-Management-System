package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.MedicalRecordRequest;
import by.darkvelton69.inputproject.dto.MedicalRecordResponse;
import by.darkvelton69.inputproject.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polyclinic34/medicalrecord")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecordResponse addMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest){
        return medicalRecordService.addMedicalRecord(medicalRecordRequest);
    }

    @PatchMapping("/edit-medical-record")
    public ResponseEntity<MedicalRecordResponse> editMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest){
        return ResponseEntity.ok(medicalRecordService.editMedicalRecord(medicalRecordRequest));
    }
}
