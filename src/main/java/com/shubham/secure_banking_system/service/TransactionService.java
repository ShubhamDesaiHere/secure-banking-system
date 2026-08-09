package com.shubham.secure_banking_system.service;

import com.shubham.secure_banking_system.dto.request.DepositRequest;
import com.shubham.secure_banking_system.dto.request.WithdrawRequest;
import com.shubham.secure_banking_system.dto.request.TransferRequest;
import com.shubham.secure_banking_system.dto.response.TransactionResponse;
import com.shubham.secure_banking_system.dto.response.TransactionHistoryResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse deposit(DepositRequest request);

    TransactionResponse withdraw(WithdrawRequest request);

    TransactionResponse transfer(TransferRequest request);

    List<TransactionHistoryResponse> getTransactionHistory(Long accountId);
}