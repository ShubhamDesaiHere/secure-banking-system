package com.shubham.secure_banking_system.dto.response;

import java.math.BigDecimal;

public class TransactionResponse {

    private String message;
    private String accountNumber;
    private BigDecimal balance;

    public TransactionResponse() {
    }

    public TransactionResponse(String message,
                               String accountNumber,
                               BigDecimal balance) {
        this.message = message;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}