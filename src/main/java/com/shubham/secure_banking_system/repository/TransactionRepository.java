package com.shubham.secure_banking_system.repository;

import com.shubham.secure_banking_system.entity.Transaction;
import com.shubham.secure_banking_system.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTransactionType(TransactionType transactionType);

}