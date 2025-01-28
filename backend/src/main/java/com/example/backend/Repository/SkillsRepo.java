package com.example.backend.Repository;

import com.example.backend.Entity.SkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillsRepo extends JpaRepository<SkillsEntity,UUID> {
}
