package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.DepositRequest;
import com.shubham.secure_banking_system.dto.request.TransferRequest;
import com.shubham.secure_banking_system.dto.request.WithdrawRequest;
import com.shubham.secure_banking_system.dto.response.TransactionHistoryResponse;
import com.shubham.secure_banking_system.dto.response.TransactionResponse;
import com.shubham.secure_banking_system.entity.Account;
import com.shubham.secure_banking_system.entity.Transaction;
import com.shubham.secure_banking_system.enums.TransactionType;
import com.shubham.secure_banking_system.exception.InsufficientBalanceException;
import com.shubham.secure_banking_system.exception.ResourceNotFoundException;
import com.shubham.secure_banking_system.repository.AccountRepository;
import com.shubham.secure_banking_system.repository.TransactionRepository;
import com.shubham.secure_banking_system.service.TransactionService;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

        private final AccountRepository accountRepository;
        private final TransactionRepository transactionRepository;

        public TransactionServiceImpl(
                        AccountRepository accountRepository,
                        TransactionRepository transactionRepository) {

                this.accountRepository = accountRepository;
                this.transactionRepository = transactionRepository;
        }

        // =========================
        // DEPOSIT
        // =========================

        @Override
        @Transactional
        public TransactionResponse deposit(DepositRequest request) {

                Account account = accountRepository
                                .findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

                // CUSTOMER → only own account
                // ADMIN → any account
                validateAccountAccess(account);

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

        // =========================
        // WITHDRAW
        // =========================

        @Override
        @Transactional
        public TransactionResponse withdraw(WithdrawRequest request) {

                Account account = accountRepository
                                .findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

                // CUSTOMER → only own account
                // ADMIN → any account
                validateAccountAccess(account);

                if (account.getBalance().compareTo(request.getAmount()) < 0) {
                        throw new InsufficientBalanceException(
                                        "Insufficient balance");
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

        // =========================
        // TRANSFER
        // =========================

        @Override
        @Transactional
        public TransactionResponse transfer(TransferRequest request) {

                Account sender = accountRepository
                                .findByAccountNumber(request.getFromAccount())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Sender account not found"));

                Account receiver = accountRepository
                                .findByAccountNumber(request.getToAccount())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Receiver account not found"));

                // CUSTOMER → sender must belong to logged-in customer
                // ADMIN → can use any sender account
                validateAccountAccess(sender);

                if (sender.getAccountNumber()
                                .equals(receiver.getAccountNumber())) {

                        throw new RuntimeException(
                                        "Cannot transfer to the same account");
                }

                if (sender.getBalance()
                                .compareTo(request.getAmount()) < 0) {

                        throw new InsufficientBalanceException(
                                        "Insufficient balance");
                }

                // Deduct from sender
                sender.setBalance(
                                sender.getBalance()
                                                .subtract(request.getAmount()));

                // Add to receiver
                receiver.setBalance(
                                receiver.getBalance()
                                                .add(request.getAmount()));

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

        // =========================
        // TRANSACTION HISTORY
        // =========================

        @Override
        @Transactional(readOnly = true)
        public List<TransactionHistoryResponse> getTransactionHistory(
                        Long accountId) {

                Account account = accountRepository
                                .findById(accountId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Account not found"));

                // CUSTOMER → only own account
                // ADMIN → any account
                validateAccountAccess(account);

                List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

                return transactions.stream()
                                .map(transaction -> new TransactionHistoryResponse(
                                                transaction.getId(),
                                                transaction.getTransactionType(),
                                                transaction.getAmount(),
                                                transaction.getTransactionDate()))
                                .toList();
        }

        // =========================
        // ACCOUNT ACCESS CHECK
        // =========================

        private void validateAccountAccess(Account account) {

                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                if (authentication == null ||
                                !authentication.isAuthenticated()) {

                        throw new AccessDeniedException(
                                        "User is not authenticated");
                }

                // Check ADMIN role
                boolean isAdmin = authentication
                                .getAuthorities()
                                .stream()
                                .anyMatch(authority -> authority.getAuthority()
                                                .equals("ROLE_ADMIN"));

                // ADMIN can access any account
                if (isAdmin) {
                        return;
                }

                // Logged-in username
                String loggedInUsername = authentication.getName();

                // Account owner's username
                String accountOwnerUsername = account.getCustomer()
                                .getUser()
                                .getUsername();

                // CUSTOMER can access only own account
                if (!loggedInUsername.equals(accountOwnerUsername)) {

                        throw new AccessDeniedException(
                                        "You can access only your own account");
                }
        }
}