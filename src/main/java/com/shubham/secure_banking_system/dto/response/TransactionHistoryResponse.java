package com.shubham.secure_banking_system.dto.response;

import com.shubham.secure_banking_system.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryResponse {

    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public TransactionHistoryResponse() {
    }

    public TransactionHistoryResponse(
            Long id,
            TransactionType transactionType,
            BigDecimal amount,
            LocalDateTime transactionDate) {

        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}