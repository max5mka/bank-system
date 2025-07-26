package com.example.bankcards.controller.api.response.card;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardResponseForAdmin extends CardResponseAbstract {

    private String hiddenNumber;
    private Instant createdAt;
    private Instant updatedAt;
}
