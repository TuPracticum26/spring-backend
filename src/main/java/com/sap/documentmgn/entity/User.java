package com.sap.documentmgn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/])[A-Za-z\\d!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]+$", message = "Password must contain at least 1 letter, 1 number, and 1 special character!")
    private String password;

    @NotBlank
    private List<ROLES> role;

    public void addRole(ROLES role) {
        if (this.role == null) {
            this.role = new ArrayList<>();
        }
        if (!this.role.contains(role)) {
            this.role.add(role);
        }
    }
}