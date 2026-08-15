package com.shubham.secure_banking_system.controller;

import com.shubham.secure_banking_system.dto.response.AdminCustomerResponse;
import com.shubham.secure_banking_system.dto.response.AdminUserResponse;
import com.shubham.secure_banking_system.entity.Customer;
import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.repository.CustomerRepository;
import com.shubham.secure_banking_system.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.shubham.secure_banking_system.dto.response.AdminAccountResponse;
import com.shubham.secure_banking_system.entity.Account;
import com.shubham.secure_banking_system.repository.AccountRepository;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

        private final UserRepository userRepository;
        private final CustomerRepository customerRepository;
        private final AccountRepository accountRepository;

        public AdminController(
                        UserRepository userRepository,
                        CustomerRepository customerRepository,
                        AccountRepository accountRepository) {

                this.userRepository = userRepository;
                this.customerRepository = customerRepository;
                this.accountRepository = accountRepository;
        }

        // =========================
        // GET ALL USERS
        // =========================

        @GetMapping("/users")
        public List<AdminUserResponse> getAllUsers() {

                return userRepository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        private AdminUserResponse mapToResponse(User user) {

                return new AdminUserResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getRole().getRoleName(),
                                user.getEnabled(),
                                user.getAccountLocked());
        }

        // =========================
        // GET ALL CUSTOMERS
        // =========================

        @GetMapping("/customers")
        public List<AdminCustomerResponse> getAllCustomers() {

                return customerRepository.findAll()
                                .stream()
                                .map(customer -> new AdminCustomerResponse(
                                                customer.getId(),
                                                customer.getFullName(),
                                                customer.getAadhaarNumber(),
                                                customer.getDateOfBirth(),
                                                customer.getUser().getId()))
                                .toList();
        }

        @GetMapping("/accounts")
        public List<AdminAccountResponse> getAllAccounts() {

                return accountRepository.findAll()
                                .stream()
                                .map(account -> new AdminAccountResponse(
                                                account.getId(),
                                                account.getAccountNumber(),
                                                account.getAccountType(),
                                                account.getBalance(),
                                                account.getCustomer().getId(),
                                                account.getCustomer().getFullName()))
                                .toList();
        }
}