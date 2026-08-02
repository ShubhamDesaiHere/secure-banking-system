package com.shubham.secure_banking_system.repository;

import com.shubham.secure_banking_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAadhaarNumber(String aadhaarNumber);

    boolean existsByAadhaarNumber(String aadhaarNumber);

    Optional<Customer> findByUserId(Long userId);

}