package com.example.bankcards.controller.api.response.bankquery;

import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.enums.BankQueryType;
import lombok.Data;

import java.time.Instant;

@Data
public class BankQueryResponseAbstract {

    private Long id;
    private BankQueryType bankQueryType;
    private BankQueryStatus bankQueryStatus;
    private Instant createdAt;
    private Instant updatedAt;
    private String explanation;
}
