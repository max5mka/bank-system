package com.example.bankcards.dao.mapping;

import com.example.bankcards.controller.api.CardResponse;
import com.example.bankcards.dao.dto.CardDto;
import com.example.bankcards.dao.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapping {
    @Mapping(target = "userId", expression = "java(card.getUser().getId())")
    CardDto toDto(Card card);
    CardResponse toResponse(CardDto cardDto);
}
