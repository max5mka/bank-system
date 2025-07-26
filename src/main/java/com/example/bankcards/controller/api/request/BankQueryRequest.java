package com.example.bankcards.controller.api.request;

import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.enums.BankQueryType;
import lombok.Data;

@Data
public class BankQueryRequest {

    private Long userId;
    private Long cardId;
    private BankQueryType bankQueryType;
    private BankQueryStatus bankQueryStatus;
    private String explanation;
}
