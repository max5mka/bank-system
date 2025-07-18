package com.example.bankcards.controller.api;

import com.example.bankcards.dao.CardStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class CardResponse {

    private Long id;
    private String number;
    private String owner;
    private YearMonth expiryDate;
    private CardStatus status;
    private BigDecimal balance;
}
