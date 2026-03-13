package by.darkvelton69.inputproject.dto;

public record UserResponse (
    Long id,
    String firstName,
    String surName,
    String lastName,
    Long age,
    String email
    )
{
}
