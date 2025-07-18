package com.example.bankcards.service;

import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.dto.UserDto;
import com.example.bankcards.dao.entity.Card;
import com.example.bankcards.dao.entity.User;
import com.example.bankcards.dao.mapping.CardMapping;
import com.example.bankcards.dao.repository.CardRepository;
import com.example.bankcards.dao.repository.UserRepository;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.util.CardEncryptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;
    private UserRepository userRepository;
    private CardMapping cardMapping;
    private CardEncryptor cardEncryptor;

    public CardDto getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .map(cardMapping::toDto)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    public List<CardDto> getAllCards() {
        var list = cardRepository.findAll();
        return list.stream()
                .map(cardMapping::toDto).toList();
    }

    public List<CardDto> getUserCards(Long userId) {
        return cardRepository.findAllByUserId(userId).stream()
                .map(cardMapping::toDto).toList();
    }

    public CardDto create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        String cardNumber = CardNumberService.generateCardNumber();
        Card card = new Card(
                CardNumberService.hideNumber(cardNumber),
                cardEncryptor.encrypt(cardNumber),
                user.getName(),
                YearMonth.now().plusYears(5),
                user
        );
        Card createdCard = cardRepository.save(card);
        return cardMapping.toDto(createdCard);
    }

    public void deleteAll() {
        cardRepository.deleteAll();
    }
}
