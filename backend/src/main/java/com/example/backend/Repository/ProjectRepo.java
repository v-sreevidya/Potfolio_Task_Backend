package com.example.backend.Repository;

import com.example.backend.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepo extends JpaRepository <ProjectEntity, UUID> {

}
