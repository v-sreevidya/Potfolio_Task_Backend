package com.example.backend.Service;

import com.example.backend.DTO.AdminDTO;
import com.example.backend.Entity.Admin;
import com.example.backend.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register new admin
    public String registerAdmin(AdminDTO adminDTO) {
        // Check if user already exists
        if (adminRepo.findByUsername(adminDTO.getUsername()) != null) {
            return "User already exists"; // Return message if admin exists
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());

        // Create and save the new admin entity
        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(hashedPassword);
        adminRepo.save(admin); // Save to the database

        return "User registered successfully"; // Return success message
    }


}
