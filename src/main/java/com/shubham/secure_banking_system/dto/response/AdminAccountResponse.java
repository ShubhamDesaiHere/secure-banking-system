package com.shubham.secure_banking_system.dto.response;

import com.shubham.secure_banking_system.enums.AccountType;

import java.math.BigDecimal;

public class AdminAccountResponse {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private Long customerId;
    private String customerName;

    public AdminAccountResponse(
            Long id,
            String accountNumber,
            AccountType accountType,
            BigDecimal balance,
            Long customerId,
            String customerName) {

        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
        this.customerName = customerName;
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

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}