package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.DepartmentRequest;
import by.darkvelton69.inputproject.dto.DepartmentResponse;
import by.darkvelton69.inputproject.entity.Department;
import by.darkvelton69.inputproject.mapper.DepartmentMapper;
import by.darkvelton69.inputproject.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentResponse createdDepartment(DepartmentRequest request){
        Department department = departmentMapper.toEntity(request);


        Department savedDep = departmentRepository.save(department);

        return departmentMapper.toResponse(savedDep);
    }

}
