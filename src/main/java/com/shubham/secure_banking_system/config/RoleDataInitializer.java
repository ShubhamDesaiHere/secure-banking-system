package com.shubham.secure_banking_system.config;

import com.shubham.secure_banking_system.entity.Role;
import com.shubham.secure_banking_system.enums.RoleType;
import com.shubham.secure_banking_system.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleDataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {

            if (!roleRepository.existsByRoleName(RoleType.ADMIN)) {
                roleRepository.save(
                        new Role(
                                RoleType.ADMIN,
                                "Bank Administrator"));
            }

            if (!roleRepository.existsByRoleName(RoleType.CUSTOMER)) {
                roleRepository.save(
                        new Role(
                                RoleType.CUSTOMER,
                                "Bank Customer"));
            }
        };
    }
}