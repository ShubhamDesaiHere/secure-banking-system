package com.shubham.secure_banking_system.controller;

import com.shubham.secure_banking_system.dto.request.CustomerRequest;
import com.shubham.secure_banking_system.dto.response.CustomerResponse;
import com.shubham.secure_banking_system.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/{userId}")
    public CustomerResponse createCustomer(
            @PathVariable Long userId,
            @Valid @RequestBody CustomerRequest request) {

        return customerService.createCustomer(userId, request);
    }
}