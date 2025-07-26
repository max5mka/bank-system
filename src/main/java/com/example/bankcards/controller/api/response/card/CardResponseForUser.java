package com.example.bankcards.controller.api.response.card;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardResponseForUser extends CardResponseAbstract {

    private String number;
    private BigDecimal balance;
}
