package com.example.bankcards.service;

import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.exception.CardNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserCardService {

    private CardService cardService;
    private UserService userService;

    public List<CardDto> getMyCards(String login) {
        var userDto = userService.findByLogin(login);
        return cardService.getUserCards(userDto.getId());
    }

    public List<CardDto> getMyCards(Long userId) {
        var userDto = userService.findById(userId);
        return cardService.getUserCards(userDto.getId());
    }

    public CardDto findMyCard(String login, Long cardId) {
        return findFirstCard(getMyCards(login), cardId);
    }

    public CardDto findMyCard(Long userId, Long cardId) {
        return findFirstCard(getMyCards(userId), cardId);
    }

    private CardDto findFirstCard(List<CardDto> cardList, Long cardId) {
        return cardList.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }
}
