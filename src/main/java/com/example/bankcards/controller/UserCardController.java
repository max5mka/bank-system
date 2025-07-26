package com.example.bankcards.controller;

import com.example.bankcards.controller.api.response.bankquery.BankQueryResponseForUser;
import com.example.bankcards.controller.api.response.card.CardResponseForUser;
import com.example.bankcards.dao.enums.BankQueryType;
import com.example.bankcards.dao.mapping.BankQueryMapping;
import com.example.bankcards.dao.mapping.CardMapping;
import com.example.bankcards.service.BankQueryService;
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
    private BankQueryService bankQueryService;
    private BankQueryMapping bankQueryMapping;

    @GetMapping
    @Operation(summary = "Вывести список своих карт")
    @ResponseStatus(HttpStatus.OK)
    public List<CardResponseForUser> getMyCards(@AuthenticationPrincipal UserDetails userDetails) {
        return userCardService.getMyCards(userDetails.getUsername())
                .stream()
                .map(cardMapping::toHideResponseForUser)
                .toList();
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Посмотреть свою конкретную карту")
    @ResponseStatus(HttpStatus.OK)
    public CardResponseForUser getMyCard(@PathVariable Long cardId,
                                         @AuthenticationPrincipal UserDetails userDetails
    ) {
        var cardDto = userCardService.findMyCard(userDetails.getUsername(), cardId);
        return cardMapping.toUnhideResponseForUser(cardDto);
    }

    @PostMapping
    @Operation(summary = "Отправить запрос на создание карты")
    @ResponseStatus(HttpStatus.CREATED)
    public BankQueryResponseForUser sendCardCreationQuery(@AuthenticationPrincipal UserDetails userDetails) {
        return sendBankQuery(userDetails.getUsername(), null, BankQueryType.CREATE_CARD);
    }

    @PostMapping("/{cardId}/activate")
    @Operation(summary = "Отправить запрос на активацию карты")
    @ResponseStatus(HttpStatus.CREATED)
    public BankQueryResponseForUser sendCardActivationQuery(@AuthenticationPrincipal UserDetails userDetails,
                                                            @PathVariable Long cardId
    ) {
        return sendBankQuery(userDetails.getUsername(), cardId, BankQueryType.ACTIVATE_CARD);
    }

    @PostMapping("/{cardId}/block")
    @Operation(summary = "Отправить запрос на блокировку карты")
    @ResponseStatus(HttpStatus.CREATED)
    public BankQueryResponseForUser sendCardBlockingQuery(@AuthenticationPrincipal UserDetails userDetails,
                                                          @PathVariable Long cardId
    ) {
        return sendBankQuery(userDetails.getUsername(), cardId, BankQueryType.BLOCK_CARD);
    }

    private BankQueryResponseForUser sendBankQuery(
            String login, Long cardId, BankQueryType bankQueryType
    ) {
        var bankQueryDto = bankQueryService.create(login, cardId, bankQueryType);
        return bankQueryMapping.toUserResponse(bankQueryDto);
    }
}
