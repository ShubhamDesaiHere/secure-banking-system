package com.shubham.secure_banking_system.controller;

import com.shubham.secure_banking_system.dto.request.LoginRequest;
import com.shubham.secure_banking_system.dto.response.LoginResponse;
import com.shubham.secure_banking_system.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);
    }
}