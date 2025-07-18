package com.example.bankcards.service;

import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.dto.UserDto;
import com.example.bankcards.dao.mapping.UserMapping;
import com.example.bankcards.dao.repository.UserRepository;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.util.CardEncryptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserCardService {

    private UserRepository userRepository;
    private UserMapping userMapping;
    private CardService cardService;
    private CardEncryptor cardEncryptor;

    public List<CardDto> getMyCards(String login) {
        var userDto = findUser(login);
        return cardService.getUserCards(userDto.getId());
    }

    private UserDto findUser(String login) {
        return userRepository.findByLogin(login)
                .map(userMapping::toDto)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    public CardDto getMyCard(Long cardId, String login) {
        var cardDto = getMyCards(login).stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(cardId));

        String encryptNumber = cardDto.getEncryptNumber();
        String decryptNumber = cardEncryptor.decrypt(encryptNumber);
        cardDto.setNumber(decryptNumber);
        return cardDto;
    }
}
