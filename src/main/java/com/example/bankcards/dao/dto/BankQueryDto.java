package com.example.bankcards.dao.dto;

import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.enums.BankQueryType;
import lombok.Data;

import java.time.Instant;

@Data
public class BankQueryDto {

    private Long id;
    private Long userId;
    private Long cardId;
    private BankQueryType bankQueryType;
    private BankQueryStatus bankQueryStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
