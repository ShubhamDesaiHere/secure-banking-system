package com.shubham.secure_banking_system.controller;

import com.shubham.secure_banking_system.dto.request.RegisterRequest;
import com.shubham.secure_banking_system.dto.response.RegisterResponse;
import com.shubham.secure_banking_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}