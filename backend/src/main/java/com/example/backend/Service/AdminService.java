package com.example.backend.Service;

import com.example.backend.DTO.AdminDTO;
import com.example.backend.Entity.Admin;
import com.example.backend.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String registerAdmin(AdminDTO adminDTO) {

        if (adminRepo.findByUsername(adminDTO.getUsername()) != null) {
            return "User already exists";
        }


        String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());


        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(hashedPassword);
        adminRepo.save(admin);

        return "User registered successfully";
    }
    public String loginAdmin(String username, String password) {

        Admin admin = adminRepo.findByUsername(username);


        if (admin == null) {
            return "Invalid credentials";
        }


        if (passwordEncoder.matches(password, admin.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }
    public List<AdminDTO> getAllAdmins() {
        return adminRepo.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO getAdminById(UUID id) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return convertEntityToDTO(admin);
    }

    public AdminDTO updateAdmin(UUID id, AdminDTO adminDTO) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (adminDTO.getUsername() != null) admin.setUsername(adminDTO.getUsername());
        if (adminDTO.getPassword() != null) admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));


        Admin updatedAdmin = adminRepo.save(admin);
        return convertEntityToDTO(updatedAdmin);
    }

    public void deleteAdmin(UUID id) {
        if (!adminRepo.existsById(id)) {
            throw new RuntimeException("Admin not found");
        }
        adminRepo.deleteById(id);
    }

    private AdminDTO convertEntityToDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setPassword(admin.getPassword());

        return adminDTO;
    }





    }

