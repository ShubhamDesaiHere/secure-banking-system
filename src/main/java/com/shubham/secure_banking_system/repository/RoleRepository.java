package com.shubham.secure_banking_system.repository;

import com.shubham.secure_banking_system.entity.Role;
import com.shubham.secure_banking_system.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleType roleName);

    boolean existsByRoleName(RoleType roleName);
}