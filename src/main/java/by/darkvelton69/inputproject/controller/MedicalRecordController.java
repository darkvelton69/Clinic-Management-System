package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.MedicalRecordRequest;
import by.darkvelton69.inputproject.dto.MedicalRecordResponse;
import by.darkvelton69.inputproject.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polyclinic34/medicalrecord")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService mrs;

    @PostMapping("/add")
    public MedicalRecordResponse addMedicalRecord(@RequestBody MedicalRecordRequest mrr){
        return mrs.addMedicalRecord(mrr);
    }

    @PatchMapping("/edit-medical-record")
    public ResponseEntity<MedicalRecordResponse> editMedicalRecord(@RequestBody MedicalRecordRequest mrr){
        return ResponseEntity.ok(mrs.editMedicalRecord(mrr));
    }
}
