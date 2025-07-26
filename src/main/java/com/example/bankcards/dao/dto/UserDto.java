package com.example.bankcards.dao.dto;

import com.example.bankcards.dao.enums.UserRole;
import lombok.Data;

import java.time.Instant;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String login;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
}
