package com.example.bankcards.service;

import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.entity.Card;
import com.example.bankcards.dao.entity.User;
import com.example.bankcards.dao.enums.CardStatus;
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
    private CardNumberService cardNumberService;

    public CardDto getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .map(cardMapping::toDto)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    public List<CardDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(cardMapping::toDto).toList();
    }

    public List<CardDto> getUserCards(Long userId) {
        return cardRepository.findAllByUserId(userId).stream()
                .map(cardMapping::toDto).toList();
    }

    public CardDto create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        String cardNumber = cardNumberService.generateCardNumber();
        Card card = new Card(
                cardEncryptor.encrypt(cardNumber),
                user.getName(),
                YearMonth.now().plusYears(5),
                user
        );
        Card createdCard = cardRepository.save(card);
        return cardMapping.toDto(createdCard);
    }

    public CardDto editStatus(Long id, CardStatus cardStatus) {
        Card entity = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));

        entity.setStatus(cardStatus);
        cardRepository.save(entity);
        return cardMapping.toDto(entity);
    }

    public void deleteAll() {
        cardRepository.deleteAll();
    }
}
