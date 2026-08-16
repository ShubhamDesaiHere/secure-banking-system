package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.RegisterRequest;
import com.shubham.secure_banking_system.dto.response.RegisterResponse;
import com.shubham.secure_banking_system.entity.Account;
import com.shubham.secure_banking_system.entity.Customer;
import com.shubham.secure_banking_system.entity.Role;
import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.enums.AccountType;
import com.shubham.secure_banking_system.enums.RoleType;
import com.shubham.secure_banking_system.repository.AccountRepository;
import com.shubham.secure_banking_system.repository.CustomerRepository;
import com.shubham.secure_banking_system.repository.RoleRepository;
import com.shubham.secure_banking_system.repository.UserRepository;
import com.shubham.secure_banking_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        // 1. Check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 3. Get CUSTOMER role
        Role customerRole = roleRepository
                .findByRoleName(RoleType.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Customer role not found"));

        // 4. Encrypt password
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // 5. Create User
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encryptedPassword,
                customerRole);

        User savedUser = userRepository.save(user);

        // 6. Automatically create Customer
        Customer customer = new Customer(
                request.getFullName(),
                request.getAadhaarNumber(),
                request.getDateOfBirth(),
                savedUser);

        Customer savedCustomer = customerRepository.save(customer);

        // 7. Automatically create one SAVINGS account
        String accountNumber = generateAccountNumber();

        Account account = new Account(
                accountNumber,
                AccountType.SAVINGS,
                new BigDecimal("1000"),
                savedCustomer);

        accountRepository.save(account);

        // 8. Return response
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "Registration successful. Customer and account created.");
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = String.valueOf(
                    1000000000L +
                            (long) (Math.random() * 900000000L));

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}