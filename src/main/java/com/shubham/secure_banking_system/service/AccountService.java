package com.shubham.secure_banking_system.service;

import com.shubham.secure_banking_system.dto.request.AccountRequest;
import com.shubham.secure_banking_system.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccountById(Long id);

    AccountResponse getMyAccount(String username);
}