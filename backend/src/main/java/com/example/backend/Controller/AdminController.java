package com.example.backend.Controller;

import com.example.backend.DTO.AdminDTO;
import com.example.backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(400).body(result); // Bad Request if user already exists
        } else if (result.equals("User registered successfully")) {
            return ResponseEntity.status(201).body(result); // Created
        } else {
            return ResponseEntity.status(500).body("Something went wrong"); // Internal Server Error
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminDTO adminDTO) {
        // Extract username and password from the request
        String username = adminDTO.getUsername();
        String password = adminDTO.getPassword();

        // Use authentication logic to verify username and password
        boolean authenticated = authenticateAdmin(username, password);

        if (authenticated) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    private boolean authenticateAdmin(String username, String password) {
        // For example, check username and password in the database or in memory
        // In real applications, you'd hash the password and compare it with the stored hash
        // This is just a simple check for demonstration purposes
        return "admin".equals(username) && "adminPassword".equals(password);
    }


}





