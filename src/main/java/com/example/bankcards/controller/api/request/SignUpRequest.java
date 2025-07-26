package com.example.bankcards.controller.api.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpRequest {

    @Pattern(
            regexp = "^[a-zA-Zа-яА-ЯёЁ]{1,50}$",
            message = "Name must contain only letters and be no more than 50 characters."
    )
    private String name;

    @Pattern(
            regexp = "^(?=[A-Za-z])(?=.*[A-Za-z0-9])[A-Za-z0-9_]{1,50}$",
            message = "Login must start with a letter and be no more than 50 characters."
    )
    private String login;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$",
            message = "Password must be at least 8 characters long and contain at least 1 digit, " +
                    "1 lowercase letter, 1 uppercase letter and 1 special character."
    )
    private String password;
}
