package com.shubham.secure_banking_system.service;

import com.shubham.secure_banking_system.dto.request.CustomerRequest;
import com.shubham.secure_banking_system.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(Long userId, CustomerRequest request);

}