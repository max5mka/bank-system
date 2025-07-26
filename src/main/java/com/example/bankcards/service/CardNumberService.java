package com.example.bankcards.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CardNumberService {

    private static final int LENGTH = 16;
    private static final Random random = new Random();

    public String generateCardNumber() {
        int[] cardNumber = generateBaseDigits();
        cardNumber[LENGTH - 1] = calculateCheckDigit(cardNumber);
        return formatNumber(cardNumber);
    }

    private int[] generateBaseDigits() {
        int[] digits = new int[LENGTH];
        digits[0] = 2 + random.nextInt(8);
        for (int i = 1; i < LENGTH - 1; i++) {
            digits[i] = random.nextInt(10);
        }
        digits[LENGTH - 1] = 0;
        return digits;
    }

    private int calculateCheckDigit(int[] digits) {
        int sum = 0;
        for (int i = 0; i < LENGTH; i++) {
            int digit = digits[i];
            if (i % 2 == 0) { // Чётные позиции (начинаем с 0)
                digit *= 2;
                sum += digit > 9 ? digit - 9 : digit;
            } else {
                sum += digit;
            }
        }

        return (10 - (sum % 10)) % 10;
    }

    private String formatNumber(int[] digits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            if (i > 0 && i % 4 == 0) {
                sb.append(" ");
            }
            sb.append(digits[i]);
        }
        return sb.toString();
    }

    public String hideNumber(String cardNumber) {
        int cardNumberLen = cardNumber.replace(" ", "").length();
        if (cardNumberLen != LENGTH) {
            throw new IllegalArgumentException("Invalid card number");
        }

        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
