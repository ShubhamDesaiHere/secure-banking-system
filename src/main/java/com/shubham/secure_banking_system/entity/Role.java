package com.shubham.secure_banking_system.entity;

import com.shubham.secure_banking_system.enums.RoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleType roleName;

    @Column(nullable = false)
    private String description;

    public Role() {
    }

    public Role(RoleType roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public RoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleType roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}