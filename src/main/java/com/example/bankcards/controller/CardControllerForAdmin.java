package com.example.bankcards.controller;

import com.example.bankcards.controller.api.response.card.CardResponseForUser;
import com.example.bankcards.dao.mapping.CardMapping;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/cards")
public class CardControllerForAdmin {

    private CardService cardService;
    private CardMapping cardMapping;

    @GetMapping
    @Operation(summary = "Вывод всех карт")
    @ResponseStatus(HttpStatus.OK)
    public List<CardResponseForUser> getAllCards() {
        return cardService.getAllCards()
                .stream()
                .map(cardMapping::toHideResponseForUser)
                .toList();
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Вывод конкретной карты")
    @ResponseStatus(HttpStatus.OK)
    public CardResponseForUser getCard(@PathVariable Long cardId) {
        var cardDto = cardService.getCardById(cardId);
        return cardMapping.toHideResponseForUser(cardDto);
    }

    @DeleteMapping
    @Operation(summary = "Удаление всех карт")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCard() {
        cardService.deleteAll();
    }
}
