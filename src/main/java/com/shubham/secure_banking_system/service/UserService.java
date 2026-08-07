package com.shubham.secure_banking_system.service;

import com.shubham.secure_banking_system.dto.request.RegisterRequest;
import com.shubham.secure_banking_system.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

}