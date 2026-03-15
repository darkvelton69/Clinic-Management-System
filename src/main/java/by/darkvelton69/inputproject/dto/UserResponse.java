package by.darkvelton69.inputproject.dto;

public record UserResponse (
    Long id,
    String firstName,
    String middleName,
    String lastName,
    Long age,
    String email
    )
{
}
