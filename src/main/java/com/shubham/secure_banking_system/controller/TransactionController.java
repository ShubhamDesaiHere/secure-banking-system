package com.shubham.secure_banking_system.controller;

import com.shubham.secure_banking_system.dto.request.DepositRequest;
import com.shubham.secure_banking_system.dto.request.WithdrawRequest;
import com.shubham.secure_banking_system.dto.request.TransferRequest;
import com.shubham.secure_banking_system.dto.response.TransactionResponse;
import com.shubham.secure_banking_system.dto.response.TransactionHistoryResponse;
import com.shubham.secure_banking_system.service.TransactionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public TransactionResponse deposit(
            @Valid @RequestBody DepositRequest request) {

        return transactionService.deposit(request);
    }

    @PostMapping("/withdraw")
    public TransactionResponse withdraw(
            @Valid @RequestBody WithdrawRequest request) {

        return transactionService.withdraw(request);
    }

    @PostMapping("/transfer")
    public TransactionResponse transfer(
            @Valid @RequestBody TransferRequest request) {

        return transactionService.transfer(request);
    }

    @GetMapping("/account/{accountId}")
    public List<TransactionHistoryResponse> getTransactionHistory(
            @PathVariable Long accountId) {

        return transactionService.getTransactionHistory(accountId);
    }
}