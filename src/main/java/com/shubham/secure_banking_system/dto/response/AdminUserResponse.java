package com.shubham.secure_banking_system.dto.response;

import com.shubham.secure_banking_system.enums.RoleType;

public class AdminUserResponse {

    private Long id;
    private String username;
    private String email;
    private RoleType role;
    private Boolean enabled;
    private Boolean accountLocked;

    public AdminUserResponse(
            Long id,
            String username,
            String email,
            RoleType role,
            Boolean enabled,
            Boolean accountLocked) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.accountLocked = accountLocked;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public RoleType getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }
}