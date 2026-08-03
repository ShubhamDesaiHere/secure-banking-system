package com.shubham.secure_banking_system.dto.request;

import com.shubham.secure_banking_system.enums.AccountType;
import jakarta.validation.constraints.NotNull;

public class AccountRequest {

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    public AccountRequest() {
    }

    public AccountRequest(AccountType accountType, Long customerId) {
        this.accountType = accountType;
        this.customerId = customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}