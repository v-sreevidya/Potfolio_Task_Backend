package com.example.backend.Repository;

import com.example.backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository <UserEntity, UUID> {
}
