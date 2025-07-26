package com.example.bankcards.service;

import com.example.bankcards.dao.dto.UserDto;
import com.example.bankcards.dao.mapping.UserMapping;
import com.example.bankcards.dao.repository.UserRepository;
import com.example.bankcards.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapping userMapping;

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapping::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserDto findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userMapping::toDto)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapping::toDto)
                .toList();
    }
}
