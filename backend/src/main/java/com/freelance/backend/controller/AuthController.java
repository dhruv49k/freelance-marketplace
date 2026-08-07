package com.freelance.backend.controller;

import com.freelance.backend.dto.LoginRequest;
import com.freelance.backend.dto.LoginResponse;
import com.freelance.backend.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }

}