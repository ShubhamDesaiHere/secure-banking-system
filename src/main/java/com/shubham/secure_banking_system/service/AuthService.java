package com.shubham.secure_banking_system.service;

import com.shubham.secure_banking_system.dto.request.LoginRequest;
import com.shubham.secure_banking_system.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}