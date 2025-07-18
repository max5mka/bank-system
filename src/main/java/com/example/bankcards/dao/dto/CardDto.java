package com.example.bankcards.dao.dto;

import com.example.bankcards.dao.CardStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class CardDto {

    private Long id;
    private String number;
    private String encryptNumber;
    private String owner;
    private YearMonth expiryDate;
    private CardStatus status;
    private BigDecimal balance;
    private Long userId;
}
