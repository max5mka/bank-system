package com.example.bankcards.controller.api.response.bankquery;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class BankQueryResponseForUser extends BankQueryResponseAbstract {

    private String hiddenNumber;
}
