package com.example.bankcards.controller.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {

    @NotNull(message = "Login must be filled")
    private String login;

    @NotNull(message = "Password must be filled")
    private String password;
}
