package com.example.bankcards.config;

import com.example.bankcards.dao.UserRole;
import com.example.bankcards.dao.repository.UserRepository;
import com.example.bankcards.dao.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default.login}")
    private String adminLogin;

    @Value("${admin.default.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException("Admin password error");
        }

        if (userRepository.findByLogin(adminLogin).isEmpty()) {
            User admin = new User();
            admin.setName(adminLogin);
            admin.setLogin(adminLogin);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(UserRole.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}
