package com.example.bankcards.controller.api.response.card;

import com.example.bankcards.dao.enums.CardStatus;
import lombok.Data;

import java.time.YearMonth;

@Data
public class CardResponseAbstract {

    private Long id;
    private String owner;
    private YearMonth expiryDate;
    private CardStatus status;
}
