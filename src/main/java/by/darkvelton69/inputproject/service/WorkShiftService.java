package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.WorkShiftRequest;
import by.darkvelton69.inputproject.dto.WorkShiftResponse;
import by.darkvelton69.inputproject.entity.Doctor;
import by.darkvelton69.inputproject.entity.WorkShift;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.mapper.WorkShiftMapper;
import by.darkvelton69.inputproject.repository.DoctorRepository;
import by.darkvelton69.inputproject.repository.WorkShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkShiftService {

    private final DoctorRepository doctorRepository;
    private final WorkShiftMapper workShiftMapper;
    private final WorkShiftRepository workShiftRepository;

    @Transactional
    public WorkShiftResponse registerWorkShift(WorkShiftRequest workShiftRequest){

        Doctor doctor = doctorRepository.findById(workShiftRequest.doctorId())
                .orElseThrow(()-> new NotFoundException("Врач не найден"));



        WorkShift workShift = workShiftMapper.toEntity(workShiftRequest);

        workShift.setDoctor(doctor);
        workShift.setStartTime(workShiftRequest.startTime());
        workShift.setEndTime(workShift.getEndTime());

        WorkShift savedWorkShift= workShiftRepository.save(workShift);

        return workShiftMapper.toResponse(savedWorkShift);

    }

}
