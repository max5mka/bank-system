package com.example.bankcards.controller;

import com.example.bankcards.controller.api.CardResponse;
import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.mapping.CardMapping;
import com.example.bankcards.service.UserCardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/my/cards")
public class UserCardController {

    private UserCardService userCardService;
    private CardMapping cardMapping;

    @GetMapping
    @Operation(summary = "Вывод своих карт")
    @ResponseStatus(HttpStatus.OK)
    public List<CardResponse> getMyCards(@AuthenticationPrincipal UserDetails userDetails) {
        return userCardService.getMyCards(userDetails.getUsername())
                .stream()
                .map(cardMapping::toResponse)
                .toList();
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Посмотреть конкретную карту")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse getMyCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetails userDetails) {
        var cardDto = userCardService.getMyCard(cardId, userDetails.getUsername());
        return cardMapping.toResponse(cardDto);
    }
}
