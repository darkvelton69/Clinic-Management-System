package by.darkvelton69.inputproject.dto;

import java.math.BigInteger;

public record DoctorResponse(
        Long id,
        String jobTitle,
        String cabinet,
        DepartmentResponse department,
        UserResponse user
) {
}
