package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.DoctorRegistration;
import by.darkvelton69.inputproject.dto.DoctorResponse;
import by.darkvelton69.inputproject.entity.Department;
import by.darkvelton69.inputproject.entity.Doctor;
import by.darkvelton69.inputproject.entity.Role;
import by.darkvelton69.inputproject.entity.User;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.exception.RoleException;
import by.darkvelton69.inputproject.exception.UserAlreadyExistsException;
import by.darkvelton69.inputproject.mapper.DocUserDepMapper;
import by.darkvelton69.inputproject.mapper.DoctorMapper;
import by.darkvelton69.inputproject.repository.DepartmentRepository;
import by.darkvelton69.inputproject.repository.DoctorRepository;
import by.darkvelton69.inputproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {
    private final PasswordEncoder passwordEncoder;
    private final DocUserDepMapper docUserDepMapper;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponse registerDoctor(DoctorRegistration registration) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (currentUser.getRole() != Role.ADMIN) {
            throw new RoleException("Данная функция доступна только админу");
        }

        if (userRepository.existsByEmail(registration.email())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже есть");
        }

        Department department = departmentRepository.findById(registration.departmentId())
                .orElseThrow(() -> new NotFoundException("Департамент не найден"));


        User user = User.builder()
                .age(registration.age())
                .email(registration.email())
                .role(Role.DOCTOR)
                .firstName(registration.firstName())
                .middleName(registration.middleName())
                .lastName(registration.lastName())
                .password(passwordEncoder.encode(registration.password()))
                .build();

        user = userRepository.save(user);


        Doctor doctor = Doctor.builder()
                .cabinet(registration.cabinet())
                .jobTitle(registration.jobTitle())
                .appointmentDuration(registration.appointmentDuration())
                .department(department)
                .user(user)
                .build();


        Doctor savedDoc = doctorRepository.save(doctor);


        return docUserDepMapper.toResponse(savedDoc);


    }

    public Page<DoctorResponse> getAllDoctorByJobTitle(String jobTitle,int page, int size) {

        Pageable pageable = PageRequest.of(
                Math.max(0, page-1),
                size,
                Sort.by("user.lastName").ascending()
        );

        Page<Doctor> DoctorPage = doctorRepository.findAllByJobTitle(jobTitle, pageable);

        return DoctorPage.map(doctorMapper::toResponse);
    }


    public DoctorResponse getDoctor(Long id) {
        return doctorRepository.findById(id)
                .map(docUserDepMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Врач не найден"));
    }
}
