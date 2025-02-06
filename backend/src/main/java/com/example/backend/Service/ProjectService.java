package com.example.backend.Service;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.Entity.ProjectEntity;
import com.example.backend.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public ProjectDTO mapToDTO(ProjectEntity projectEntity) {
        ProjectDTO dto = new ProjectDTO();
        dto.setTitle(projectEntity.getTitle());
        dto.setDetails(projectEntity.getDetails());

        // Convert byte[] to Base64 String
        if (projectEntity.getImage() != null) {
            dto.setImage(Base64.getEncoder().encodeToString(projectEntity.getImage()));
        }

        return dto;
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

    public ProjectDTO updateProjectById(UUID id, ProjectDTO projectDTO) {

        ProjectEntity existingProject = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        System.out.println("Existing Project before update: " + existingProject);

        if (projectDTO.getTitle() != null) {
            existingProject.setTitle(projectDTO.getTitle());
        }
        if (projectDTO.getDetails() != null) {
            existingProject.setDetails(projectDTO.getDetails());
        }
        if (projectDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(projectDTO.getImage());
            existingProject.setImage(imageBytes);
        }
        System.out.println("Existing Project after update: " + existingProject);

        ProjectEntity updatedEntity = projectRepo.save(existingProject);
        return convertEntityToDTO(updatedEntity);
    }
    public List<ProjectDTO> getAllProjects() {
        return projectRepo.findAll().stream()
                .map(project -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setId(project.getId());
                    dto.setTitle(project.getTitle());
                    dto.setDetails(project.getDetails());
                    dto.setImage(Arrays.toString(project.getImage()));
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public ProjectDTO saveProject(ProjectDTO projectDTO) {

        ProjectEntity projectEntity = convertDTOToEntity(projectDTO);
        ProjectEntity savedEntity = projectRepo.save(projectEntity);
        return convertEntityToDTO(savedEntity);
    }

    private ProjectEntity convertDTOToEntity(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setTitle(projectDTO.getTitle());
        projectEntity.setDetails(projectDTO.getDetails());


        if (projectDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(projectDTO.getImage());
            projectEntity.setImage(imageBytes);
        }

        return projectEntity;
    }

    public void deleteServiceById(UUID id) {
        if (!projectRepo.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepo.deleteById(id);
    }
    }

