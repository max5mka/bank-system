package com.example.bankcards.controller.api;

import lombok.Data;

@Data
public class SignInRequest {

    private String login;
    private String password;
}
