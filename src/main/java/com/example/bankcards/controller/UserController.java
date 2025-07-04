package com.example.bankcards.controller;

import com.example.bankcards.dao.dto.UserDto;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping()
    @Operation(summary = "Вывод всех пользователей")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

}
