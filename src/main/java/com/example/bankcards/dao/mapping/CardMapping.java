package com.example.bankcards.dao.mapping;

import com.example.bankcards.controller.api.response.card.CardResponseAbstract;
import com.example.bankcards.controller.api.response.card.CardResponseForAdmin;
import com.example.bankcards.controller.api.response.card.CardResponseForUser;
import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.entity.Card;
import com.example.bankcards.service.CardNumberService;
import com.example.bankcards.util.CardEncryptor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mapper(componentModel = "spring")
public abstract class CardMapping {

    @Autowired
    private CardEncryptor cardEncryptor;

    @Autowired
    private CardNumberService cardNumberService;

    @Mapping(target = "userId", expression = "java(card.getUser().getId())")
    public abstract CardDto toDto(Card card);

    public CardResponseForUser toHideResponseForUser(CardDto cardDto) {
        return buildResponse(
                cardDto,
                number -> cardNumberService.hideNumber(number),
                CardResponseForUser::new,
                (response, hiddenNumber) -> {
                    response.setNumber( hiddenNumber );
                    response.setBalance( cardDto.getBalance() );
                });
    }

    public CardResponseForUser toUnhideResponseForUser(CardDto cardDto) {
        return buildResponse(
                cardDto,
                number -> number,
                CardResponseForUser::new,
                (response, number) -> {
                    response.setNumber( number );
                    response.setBalance( cardDto.getBalance() );
                });
    }

    public CardResponseForAdmin toResponseForAdmin(CardDto cardDto) {
        return buildResponse(
                cardDto,
                number -> cardNumberService.hideNumber(number),
                CardResponseForAdmin::new,
                (response, hiddenNumber) -> {
                    response.setHiddenNumber(hiddenNumber);
                    response.setCreatedAt( cardDto.getCreatedAt() );
                    response.setUpdatedAt( cardDto.getUpdatedAt() );
                }
        );
    }

    private <T extends CardResponseAbstract> T buildResponse(
            CardDto cardDto,
            Function<String, String> numberProcessor,
            Supplier<T> responseConstructor,
            BiConsumer<T, String> customizer
    ) {
        if (cardDto == null) {
            return null;
        }

        String number = cardEncryptor.decrypt(cardDto.getEncryptNumber());
        String processedNumber = numberProcessor.apply(number);

        T response = responseConstructor.get();
        response.setId( cardDto.getId() );
        response.setOwner( cardDto.getOwner() );
        response.setExpiryDate( cardDto.getExpiryDate() );
        response.setStatus( cardDto.getStatus() );

        customizer.accept(response, processedNumber);
        return response;
    }
}
