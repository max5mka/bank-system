package com.example.bankcards.dao.dto;

import com.example.bankcards.dao.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String login;
    private String password;
    private UserRole role;
}
