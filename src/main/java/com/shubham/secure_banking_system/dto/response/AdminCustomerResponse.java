package com.shubham.secure_banking_system.dto.response;

import java.time.LocalDate;

public class AdminCustomerResponse {

    private Long id;
    private String fullName;
    private String aadhaarNumber;
    private LocalDate dateOfBirth;
    private Long userId;

    public AdminCustomerResponse(
            Long id,
            String fullName,
            String aadhaarNumber,
            LocalDate dateOfBirth,
            Long userId) {

        this.id = id;
        this.fullName = fullName;
        this.aadhaarNumber = aadhaarNumber;
        this.dateOfBirth = dateOfBirth;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getUserId() {
        return userId;
    }
}