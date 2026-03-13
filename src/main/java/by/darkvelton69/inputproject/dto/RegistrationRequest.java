package by.darkvelton69.inputproject.dto;


public record RegistrationRequest(
       String firstName,
       String middleName,
       String lastName,
       Long age,
       String email,
       String password,
       String phone
) {
}
