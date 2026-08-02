package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.CustomerRequest;
import com.shubham.secure_banking_system.dto.response.CustomerResponse;
import com.shubham.secure_banking_system.entity.Customer;
import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.repository.CustomerRepository;
import com.shubham.secure_banking_system.repository.UserRepository;
import com.shubham.secure_banking_system.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerResponse createCustomer(Long userId,
                                           CustomerRequest request) {

        if (customerRepository.existsByAadhaarNumber(request.getAadhaarNumber())) {
            throw new RuntimeException("Aadhaar number already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = new Customer(
                request.getFullName(),
                request.getAadhaarNumber(),
                request.getDateOfBirth(),
                user
        );

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getFullName(),
                savedCustomer.getAadhaarNumber(),
                savedCustomer.getDateOfBirth()
        );
    }
}