package com.example.bankcards.exception;

public class WrongBankQueryStatusException extends RuntimeException {
    public WrongBankQueryStatusException(String message) {
        super(message);
    }
}
