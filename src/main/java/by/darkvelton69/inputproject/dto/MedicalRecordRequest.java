package by.darkvelton69.inputproject.dto;

import by.darkvelton69.inputproject.entity.BloodType;
import by.darkvelton69.inputproject.entity.RhFactor;

public record MedicalRecordRequest(
        BloodType bloodType,
        RhFactor rhFactor,
        String allergies,
        String chronicDiseases,
        String familyHistory,
        String insuranceNumber
) {
}
