package com.example.bankcards.dao.mapping;

import com.example.bankcards.controller.api.response.bankquery.BankQueryResponseAbstract;
import com.example.bankcards.controller.api.response.bankquery.BankQueryResponseForAdmin;
import com.example.bankcards.controller.api.response.bankquery.BankQueryResponseForUser;
import com.example.bankcards.controller.api.response.bankquery.ReviewedBankQueryResponse;
import com.example.bankcards.dao.dto.BankQueryDto;
import com.example.bankcards.dao.entity.BankQuery;
import com.example.bankcards.service.CardNumberService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardEncryptor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mapper(componentModel = "spring")
public abstract class BankQueryMapping {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardEncryptor cardEncryptor;

    @Autowired
    private CardNumberService cardNumberService;

    @Autowired
    private CardMapping cardMapping;

    public abstract BankQueryDto toDto(BankQuery card);

    public BankQueryResponseForUser toUserResponse(BankQueryDto bankQueryDto) {
        return buildResponse(
                bankQueryDto,
                BankQueryResponseForUser::new,
                response ->
                        response.setHiddenNumber(bankQueryDto.getCardId() == null
                                ? null
                                : getHiddenNumber(bankQueryDto.getCardId()))
        );
    }

    public BankQueryResponseForAdmin toAdminResponse(BankQueryDto bankQueryDto) {
        return buildResponse(
                bankQueryDto,
                BankQueryResponseForAdmin::new,
                response -> {
                    response.setUserId(bankQueryDto.getUserId());
                    response.setHiddenNumber(bankQueryDto.getCardId() == null
                            ? null
                            : getHiddenNumber(bankQueryDto.getCardId()));
                }
        );
    }

    private String getHiddenNumber(Long cardId) {
        String encryptNumber = cardService.getCardById(cardId).getEncryptNumber();
        return cardNumberService.hideNumber(
                cardEncryptor.decrypt(encryptNumber)
        );
    }

    public ReviewedBankQueryResponse toReviewedResponse(BankQueryDto bankQueryDto) {
        var cardDto = cardService.getCardById(bankQueryDto.getCardId());

        return buildResponse(
                bankQueryDto,
                ReviewedBankQueryResponse::new,
                response -> {
                    response.setUserId(bankQueryDto.getUserId());
                    response.setCardResponseForAdmin(cardMapping.toResponseForAdmin(cardDto));
                }
        );
    }

    public <T extends BankQueryResponseAbstract> T buildResponse(
            BankQueryDto bankQueryDto,
            Supplier<T> responseConstructor,
            Consumer<T> customizer
    ) {

        T response = responseConstructor.get();
        response.setId(bankQueryDto.getId());
        response.setBankQueryType(bankQueryDto.getBankQueryType());
        response.setBankQueryStatus(bankQueryDto.getBankQueryStatus());
        response.setCreatedAt(bankQueryDto.getCreatedAt());
        response.setUpdatedAt(bankQueryDto.getUpdatedAt());

        customizer.accept(response);
        return response;
    }
}
