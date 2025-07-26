package com.example.bankcards.controller.api.response.bankquery;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankQueryResponseForAdmin extends BankQueryResponseAbstract {

    private Long userId;
    private String hiddenNumber;
}
