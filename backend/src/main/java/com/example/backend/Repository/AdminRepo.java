package com.example.backend.Repository;

import com.example.backend.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepo extends JpaRepository<Admin, UUID> {
    Admin findByUsername(String username);
}
