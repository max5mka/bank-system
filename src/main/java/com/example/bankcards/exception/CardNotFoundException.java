package com.example.bankcards.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Long cardId) {
        super("Card with id " + cardId + " was not found");
    }
}
