package com.example.bankcards.controller;

import com.example.bankcards.controller.api.CardResponse;
import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.dto.UserDto;
import com.example.bankcards.dao.mapping.CardMapping;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class AdminUserController {

    private UserService userService;
    private CardService cardService;
    private CardMapping cardMapping;

    @GetMapping
    @Operation(summary = "Вывод всех пользователей")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Вывод пользователя по id")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{userId}/cards")
    @Operation(summary = "Вывод карт пользователя")
    @ResponseStatus(HttpStatus.OK)
    public List<CardDto> getUserCards(@PathVariable Long userId) {
        return cardService.getUserCards(userId);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание банковской карты пользователю")
    public CardResponse create(@PathVariable Long userId) {
        CardDto createdCardDto = cardService.create(userId);
        return cardMapping.toResponse(createdCardDto);
    }
}
