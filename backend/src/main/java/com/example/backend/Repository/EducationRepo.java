package com.example.backend.Repository;

import com.example.backend.Entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationRepo extends JpaRepository<EducationEntity, UUID> {
}
