package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.DepartmentRequest;
import by.darkvelton69.inputproject.dto.DepartmentResponse;
import by.darkvelton69.inputproject.entity.Department;
import by.darkvelton69.inputproject.entity.Role;
import by.darkvelton69.inputproject.entity.User;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.exception.RoleException;
import by.darkvelton69.inputproject.mapper.DepartmentMapper;
import by.darkvelton69.inputproject.repository.DepartmentRepository;
import by.darkvelton69.inputproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponse createdDepartment(DepartmentRequest request){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Пользователь не найден"));

        if(currentUser.getRole()!= Role.ADMIN){
            throw new RoleException("Данная функция доступна только админу");
        }

        Department department = departmentMapper.toEntity(request);


        Department savedDep = departmentRepository.save(department);

        return departmentMapper.toResponse(savedDep);
    }

}
