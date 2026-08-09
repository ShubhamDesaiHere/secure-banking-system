package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.DepositRequest;
import com.shubham.secure_banking_system.dto.request.WithdrawRequest;
import com.shubham.secure_banking_system.dto.response.TransactionResponse;
import com.shubham.secure_banking_system.entity.Account;
import com.shubham.secure_banking_system.entity.Transaction;
import com.shubham.secure_banking_system.enums.TransactionType;
import com.shubham.secure_banking_system.repository.AccountRepository;
import com.shubham.secure_banking_system.repository.TransactionRepository;
import com.shubham.secure_banking_system.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.shubham.secure_banking_system.dto.request.TransferRequest;
import com.shubham.secure_banking_system.exception.InsufficientBalanceException;
import com.shubham.secure_banking_system.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.shubham.secure_banking_system.dto.response.TransactionHistoryResponse;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

        private final AccountRepository accountRepository;
        private final TransactionRepository transactionRepository;

        public TransactionServiceImpl(AccountRepository accountRepository,
                        TransactionRepository transactionRepository) {
                this.accountRepository = accountRepository;
                this.transactionRepository = transactionRepository;

        }

        @Override
        public TransactionResponse deposit(DepositRequest request) {

                Account account = accountRepository
                                .findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new RuntimeException("Account not found"));

                BigDecimal newBalance = account.getBalance().add(request.getAmount());

                account.setBalance(newBalance);

                accountRepository.save(account);

                Transaction transaction = new Transaction(
                                TransactionType.DEPOSIT,
                                request.getAmount(),
                                LocalDateTime.now(),
                                account);

                transactionRepository.save(transaction);

                return new TransactionResponse(
                                "Amount deposited successfully",
                                account.getAccountNumber(),
                                account.getBalance());

        }

        @Override
        public TransactionResponse withdraw(WithdrawRequest request) {

                Account account = accountRepository
                                .findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new RuntimeException("Account not found"));

                if (account.getBalance().compareTo(request.getAmount()) < 0) {
                        throw new RuntimeException("Insufficient balance");
                }

                BigDecimal newBalance = account.getBalance().subtract(request.getAmount());

                account.setBalance(newBalance);

                accountRepository.save(account);

                Transaction transaction = new Transaction(
                                TransactionType.WITHDRAW,
                                request.getAmount(),
                                LocalDateTime.now(),
                                account);

                transactionRepository.save(transaction);

                return new TransactionResponse(
                                "Amount withdrawn successfully",
                                account.getAccountNumber(),
                                account.getBalance());
        }

        @Override
        @Transactional
        public TransactionResponse transfer(TransferRequest request) {

                Account sender = accountRepository
                                .findByAccountNumber(request.getFromAccount())
                                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found"));

                Account receiver = accountRepository
                                .findByAccountNumber(request.getToAccount())
                                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));

                if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
                        throw new RuntimeException("Cannot transfer to the same account");
                }

                if (sender.getBalance().compareTo(request.getAmount()) < 0) {
                        throw new InsufficientBalanceException("Insufficient balance");
                }

                // Deduct money from sender
                sender.setBalance(
                                sender.getBalance().subtract(request.getAmount()));

                // Add money to receiver
                receiver.setBalance(
                                receiver.getBalance().add(request.getAmount()));

                accountRepository.save(sender);
                accountRepository.save(receiver);

                // Sender transaction
                Transaction senderTransaction = new Transaction(
                                TransactionType.TRANSFER,
                                request.getAmount(),
                                LocalDateTime.now(),
                                sender);

                // Receiver transaction
                Transaction receiverTransaction = new Transaction(
                                TransactionType.TRANSFER,
                                request.getAmount(),
                                LocalDateTime.now(),
                                receiver);

                transactionRepository.save(senderTransaction);
                transactionRepository.save(receiverTransaction);

                return new TransactionResponse(
                                "Money transferred successfully",
                                sender.getAccountNumber(),
                                sender.getBalance());
        }

        @Override
        public List<TransactionHistoryResponse> getTransactionHistory(Long accountId) {

                List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

                return transactions.stream()
                                .map(transaction -> new TransactionHistoryResponse(
                                                transaction.getId(),
                                                transaction.getTransactionType(),
                                                transaction.getAmount(),
                                                transaction.getTransactionDate()))
                                .toList();
        }
}
