package by.darkvelton69.inputproject.dto;


public record DoctorRegistration(
        String firstName,
        String middleName,
        String lastName,
        Long age,
        String email,
        String password,
        String jobTitle,
        String cabinet,
        Long departmentId,
        Integer appointmentDuration
) {
}
