package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.AccountRequest;
import com.shubham.secure_banking_system.dto.response.AccountResponse;
import com.shubham.secure_banking_system.entity.Account;
import com.shubham.secure_banking_system.entity.Customer;
import com.shubham.secure_banking_system.repository.AccountRepository;
import com.shubham.secure_banking_system.repository.CustomerRepository;
import com.shubham.secure_banking_system.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        String accountNumber = generateAccountNumber();

        BigDecimal balance;

        if (request.getAccountType().name().equals("SAVINGS")) {
            balance = new BigDecimal("1000");
        } else {
            balance = new BigDecimal("5000");
        }

        Account account = new Account(
                accountNumber,
                request.getAccountType(),
                balance,
                customer);

        Account savedAccount = accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getAccountType(),
                savedAccount.getBalance());
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = String.valueOf(
                    1000000000L + (long) (Math.random() * 900000000L));
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    @Override
    public AccountResponse getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance());
    }

}