package by.darkvelton69.inputproject.dto;

import by.darkvelton69.inputproject.entity.Role;

public record DoctorRegistration(
        String firstName,
        String middleName,
        String lastName,
        Long age,
        String email,
        Role role,
        String password,
        String jobTitle,
        String cabinet,
        Long departmentId
) {
}
