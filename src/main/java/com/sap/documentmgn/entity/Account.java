package com.sap.documentmgn.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="ACCOUNT")
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name="first_name", length=50, nullable = false)
    private String firstName;

    @Column(name="last_name", length=50)
    private String lastName;

    public long getId(){
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}