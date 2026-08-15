package com.shubham.secure_banking_system.config;

import com.shubham.secure_banking_system.entity.Role;
import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.enums.RoleType;
import com.shubham.secure_banking_system.repository.RoleRepository;
import com.shubham.secure_banking_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initializeRolesAndAdmin(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Create CUSTOMER role if it does not exist
            Role customerRole = roleRepository
                    .findByRoleName(RoleType.CUSTOMER)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName(RoleType.CUSTOMER);
                        role.setDescription("Bank Customer");
                        return roleRepository.save(role);
                    });

            // Create ADMIN role if it does not exist
            Role adminRole = roleRepository
                    .findByRoleName(RoleType.ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName(RoleType.ADMIN);
                        role.setDescription("Bank Administrator");
                        return roleRepository.save(role);
                    });

            // Create the only initial admin if it does not exist
            if (!userRepository.existsByUsername("admin")) {

                User admin = new User(
                        "admin",
                        "admin@bank.com",
                        passwordEncoder.encode("Admin@123"),
                        adminRole);

                userRepository.save(admin);

                System.out.println("=================================");
                System.out.println("ADMIN USER CREATED");
                System.out.println("Username: admin");
                System.out.println("Password: Admin@123");
                System.out.println("=================================");
            }
        };
    }
}