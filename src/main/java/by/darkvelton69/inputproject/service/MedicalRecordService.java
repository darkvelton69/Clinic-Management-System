package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.MedicalRecordRequest;
import by.darkvelton69.inputproject.dto.MedicalRecordResponse;
import by.darkvelton69.inputproject.entity.MedicalRecord;
import by.darkvelton69.inputproject.entity.User;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.mapper.MedicalMapper;
import by.darkvelton69.inputproject.repository.MedicalRecordRepository;
import by.darkvelton69.inputproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {

    private final UserRepository userRepository;
    private final MedicalMapper medicalMapper;
    private final MedicalRecordRepository medicalRecordRepository;


    @Transactional
    public MedicalRecordResponse addMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );

        MedicalRecord medicalRecord = medicalMapper.toEntity(medicalRecordRequest);

        medicalRecord.setClient(user.getClient());

        MedicalRecord mrSaved = medicalRecordRepository.save(medicalRecord);

        return medicalMapper.toResponse(mrSaved);
    }

    @Transactional
    public MedicalRecordResponse editMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        MedicalRecord medicalRecord = medicalRecordRepository.findByClientUserEmail(email).orElseThrow(() -> new NotFoundException("Медицинская карта не найдена"));


        medicalMapper.updateEntityFromDto(medicalRecordRequest, medicalRecord);

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);

        return medicalMapper.toResponse(savedMedicalRecord);
    }
}
