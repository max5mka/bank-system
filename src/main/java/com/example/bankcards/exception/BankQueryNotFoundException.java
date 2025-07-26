package com.example.bankcards.exception;

public class BankQueryNotFoundException extends RuntimeException {

    public BankQueryNotFoundException(Long cardQueryId) {
        super("Card query with id " + cardQueryId + " was not found");
    }
}
