package com.example.backend.DTO;

import com.example.backend.Entity.Admin;

import java.util.UUID;

public class AdminDTO {
    private UUID id;
    private String username;
    private String password;

    public AdminDTO()
    {

    }

    public AdminDTO(Admin savedAdmin) {
        this.id = savedAdmin.getId();
        this.username = savedAdmin.getUsername();
        this.password = savedAdmin.getPassword();
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
