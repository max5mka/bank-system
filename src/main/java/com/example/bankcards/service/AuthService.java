package com.example.bankcards.service;

import com.example.bankcards.controller.api.SignUpRequest;
import com.example.bankcards.dao.UserRole;
import com.example.bankcards.dao.entity.User;
import com.example.bankcards.dao.mapping.UserMapping;
import com.example.bankcards.dao.repository.UserRepository;
import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;
    private UserMapping userMapping;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .map(UserDetailsImpl::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User '%s' was not found", login)));
    }

    public void signup(SignUpRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getLogin())) {
            throw new UserAlreadyExistsException(signUpRequest.getLogin());
        }

        User newUser = userMapping.toEntity(signUpRequest);
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.setRole(UserRole.ROLE_USER);
        userRepository.save(newUser);
    }
}
