package com.example.backend.Service;

import com.example.backend.Entity.Admin;
import com.example.backend.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return UserDetails object with admin credentials
        return new User(admin.getUsername(), admin.getPassword(), Collections.emptyList());
    }
}
