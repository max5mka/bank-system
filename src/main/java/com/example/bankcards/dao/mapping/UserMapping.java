package com.example.bankcards.dao.mapping;

import com.example.bankcards.controller.api.request.SignUpRequest;
import com.example.bankcards.dao.dto.UserDto;
import com.example.bankcards.dao.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapping {

    UserDto toDto(User user);

    User toEntity(SignUpRequest signUpRequest);
}
