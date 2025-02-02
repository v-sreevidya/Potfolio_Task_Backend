package com.example.backend.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "admin")
public class Admin {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


public Admin(){}


    public Admin(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
