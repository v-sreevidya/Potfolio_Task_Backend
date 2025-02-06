package com.example.backend.Controller;

import com.example.backend.DTO.AdminDTO;
import com.example.backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminDTO adminDTO) {
        String result = adminService.registerAdmin(adminDTO);


        if (result.equals("User already exists")) {
            return ResponseEntity.status(400).body(result);
        } else if (result.equals("User registered successfully")) {
            return ResponseEntity.status(201).body(result);
        } else {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminDTO adminDTO) {
        // Extract username and password from the request
        String username = adminDTO.getUsername();
        String password = adminDTO.getPassword();

        String result = adminService.loginAdmin(username, password);

        if (result.equals("Login successful")) {
            return ResponseEntity.status(200).body(result);
        } else if (result.equals("Invalid credentials")) {
            return ResponseEntity.status(401).body(result);
        } else {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable UUID id) {
        AdminDTO adminDTO = adminService.getAdminById(id);
        return ResponseEntity.ok(adminDTO);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable UUID id, @RequestBody AdminDTO adminDTO) {
        AdminDTO updatedAdmin = adminService.updateAdmin(id, adminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable UUID id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

}






