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
    private final MedicalMapper mapper;
    private final MedicalRecordRepository medicalRecordRepository;


    @Transactional
    public MedicalRecordResponse addMedicalRecord(MedicalRecordRequest mrr) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );

        MedicalRecord medicalRecord = mapper.toEntity(mrr);

        medicalRecord.setClient(user.getClient());

        MedicalRecord mrSaved = medicalRecordRepository.save(medicalRecord);

        return mapper.toResponse(mrSaved);
    }

    @Transactional
    public MedicalRecordResponse editMedicalRecord(MedicalRecordRequest mrr) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        MedicalRecord medicalRecord = medicalRecordRepository.findByClientUserEmail(email).orElseThrow(() -> new NotFoundException("Медицинская карта не найдена"));


        mapper.updateEntityFromDto(mrr,medicalRecord);

        System.out.println("Изменения полей выполнены");
        MedicalRecord mrSaved = medicalRecordRepository.save(medicalRecord);

        return mapper.toResponse(mrSaved);
    }
}
