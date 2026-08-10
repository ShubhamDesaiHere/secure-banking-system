package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.LoginRequest;
import com.shubham.secure_banking_system.dto.response.LoginResponse;
import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.repository.UserRepository;
import com.shubham.secure_banking_system.security.JwtService;
import com.shubham.secure_banking_system.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponse(token);
    }
}