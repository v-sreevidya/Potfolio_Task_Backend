package com.example.backend.Repository;

import com.example.backend.DTO.UserDTO;
import com.example.backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID> {

}
