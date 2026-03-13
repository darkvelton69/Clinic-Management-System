package by.darkvelton69.inputproject.dto;

public record ClientResponse(
        Long id,
        String medicalCardNumber,
        String phone,
        UserResponse user
) {

}
