package by.darkvelton69.inputproject.dto;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {
}
