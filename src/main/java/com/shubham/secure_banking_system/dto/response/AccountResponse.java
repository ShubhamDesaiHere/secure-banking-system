package com.shubham.secure_banking_system.dto.response;

import com.shubham.secure_banking_system.enums.AccountType;

import java.math.BigDecimal;

public class AccountResponse {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;

    public AccountResponse() {
    }

    public AccountResponse(Long id,
                           String accountNumber,
                           AccountType accountType,
                           BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}