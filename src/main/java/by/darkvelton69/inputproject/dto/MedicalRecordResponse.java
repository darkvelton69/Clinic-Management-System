package by.darkvelton69.inputproject.dto;

import by.darkvelton69.inputproject.entity.BloodType;
import by.darkvelton69.inputproject.entity.RhFactor;

import java.time.LocalDateTime;

public record MedicalRecordResponse(
        Long id,
        Long clientId,
        BloodType bloodType,
        RhFactor rhFactor,
        String allergies,
        String chronicDiseases,
        String familyHistory,
        String insuranceNumber,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
