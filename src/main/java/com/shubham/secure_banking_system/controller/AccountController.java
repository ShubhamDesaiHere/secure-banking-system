package com.shubham.secure_banking_system.controller;

import com.shubham.secure_banking_system.dto.request.AccountRequest;
import com.shubham.secure_banking_system.dto.response.AccountResponse;
import com.shubham.secure_banking_system.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse createAccount(
            @Valid @RequestBody AccountRequest request) {

        return accountService.createAccount(request);
    }

}