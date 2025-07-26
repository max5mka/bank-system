package com.example.bankcards.controller;

import com.example.bankcards.controller.api.request.SignInRequest;
import com.example.bankcards.controller.api.request.SignUpRequest;
import com.example.bankcards.security.TokenService;
import com.example.bankcards.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signup(signUpRequest);
        return "Successfully registered!";
    }

    @PostMapping("/signin")
    @Operation(summary = "Авторизация")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getLogin(),
                            signInRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login or password error");
        }

        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(String.format("Successfully authorized!\nToken: %s", token));
    }

}
