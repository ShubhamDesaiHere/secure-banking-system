package com.shubham.secure_banking_system.service;

import com.shubham.secure_banking_system.dto.request.DepositRequest;
import com.shubham.secure_banking_system.dto.response.TransactionResponse;

public interface TransactionService {

    TransactionResponse deposit(DepositRequest request);

}