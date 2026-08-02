package com.shubham.secure_banking_system.dto.response;

import java.time.LocalDate;

public class CustomerResponse {

    private Long id;
    private String fullName;
    private String aadhaarNumber;
    private LocalDate dateOfBirth;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id,
                            String fullName,
                            String aadhaarNumber,
                            LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.aadhaarNumber = aadhaarNumber;
        this.dateOfBirth = dateOfBirth;
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
}