package com.example.bankcards.controller.api.response.bankquery;

import com.example.bankcards.controller.api.response.card.CardResponseForAdmin;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewedBankQueryResponse extends BankQueryResponseAbstract {

    private Long userId;
    private CardResponseForAdmin cardResponseForAdmin;
}
