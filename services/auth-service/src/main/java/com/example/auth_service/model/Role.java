package com.example.auth_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_name", length = 50)
    private String name;  // e.g. ROLE_CUSTOMER, ROLE_SELLER, ROLE_ADMIN

    // Add this getter:
    public String getName() {
        return name;
    }

    // (Optionally, if you need to set the name:)
    public void setName(String name) {
        this.name = name;
    }
}
