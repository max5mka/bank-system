package com.example.bankcards.service.validation;

import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.enums.BankQueryType;
import com.example.bankcards.exception.WrongBankQueryStatusException;
import com.example.bankcards.service.UserCardService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankQueryValidation {

    private final UserService userService;
    private final UserCardService userCardService;

    public void validateUserExists(Long userId) {
        userService.findById(userId);
    }

    public void validateCardAccess(Long cardId, BankQueryType type, Long userId) {
        if (cardId == null && !BankQueryType.CREATE_CARD.equals(type)) {
            throw new IllegalArgumentException("Card ID is required for this query type");
        }
        if (cardId != null) {
            userCardService.findMyCard(userId, cardId);
        }
    }

    public void validateStatusTransition(BankQueryStatus current, BankQueryStatus newStatus) {
        if (!isPending(current)) {
            throw new WrongBankQueryStatusException("Only PENDING queries can be modified");
        }
        if (isPending(newStatus)) {
            throw new WrongBankQueryStatusException("Cannot change to PENDING status");
        }
    }

    public boolean isPending(BankQueryStatus status) {
        return BankQueryStatus.PENDING.equals(status);
    }
}
