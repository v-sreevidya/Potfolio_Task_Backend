package com.example.backend.Service;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.Entity.ProjectEntity;
import com.example.backend.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }


    public ProjectDTO getProjectById(UUID id) {
        ProjectEntity projectEntity = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return convertEntityToDTO(projectEntity);
    }


    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = convertDTOToEntity(projectDTO);
        ProjectEntity savedEntity = projectRepo.save(projectEntity);
        return convertEntityToDTO(savedEntity);
    }


    private ProjectDTO convertEntityToDTO(ProjectEntity projectEntity) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setTitle(projectEntity.getTitle());
        projectDTO.setDetails(projectEntity.getDetails());

        if (projectEntity.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(projectEntity.getImage());
            projectDTO.setImage(base64Image);
        }
        return projectDTO;
    }


    private ProjectEntity convertDTOToEntity(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setTitle(projectDTO.getTitle());
        projectEntity.setDetails(projectDTO.getDetails());

        if (projectDTO.getImage() != null) {
            projectEntity.setImage(Base64.getDecoder().decode(projectDTO.getImage()));
        }
        return projectEntity;
    }
}
