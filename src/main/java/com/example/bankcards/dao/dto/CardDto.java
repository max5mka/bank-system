package com.example.bankcards.dao.dto;

import com.example.bankcards.dao.enums.CardStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;

@Data
public class CardDto {

    private Long id;
    private String encryptNumber;
    private String owner;
    private YearMonth expiryDate;
    private CardStatus status;
    private BigDecimal balance;
    private Long userId;
    private Instant createdAt;
    private Instant updatedAt;
}
