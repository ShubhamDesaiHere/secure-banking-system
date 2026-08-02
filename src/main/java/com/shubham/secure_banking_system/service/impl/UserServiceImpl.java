package com.shubham.secure_banking_system.service.impl;

import com.shubham.secure_banking_system.dto.request.RegisterRequest;
import com.shubham.secure_banking_system.dto.response.RegisterResponse;
import com.shubham.secure_banking_system.entity.Role;
import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.enums.RoleType;
import com.shubham.secure_banking_system.repository.RoleRepository;
import com.shubham.secure_banking_system.repository.UserRepository;
import com.shubham.secure_banking_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
public RegisterResponse register(RegisterRequest request) {

    if (userRepository.existsByUsername(request.getUsername())) {
        throw new RuntimeException("Username already exists");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already exists");
    }

    Role role = roleRepository
            .findByRoleName(RoleType.CUSTOMER)
            .orElseThrow(() ->
                    new RuntimeException("Customer role not found"));

    String encryptedPassword =
            passwordEncoder.encode(request.getPassword());

    User user = new User(
            request.getUsername(),
            request.getEmail(),
            encryptedPassword,
            role
    );

    User savedUser = userRepository.save(user);

    return new RegisterResponse(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            "Registration Successful"
    );
}
}