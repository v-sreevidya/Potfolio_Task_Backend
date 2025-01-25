package com.example.backend.Service;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.Entity.ProjectEntity;
import com.example.backend.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;
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


    public ProjectDTO updateProject(UUID id, ProjectDTO projectDTO) {
        Optional<ProjectEntity> existingProject = projectRepo.findById(id);
        if (existingProject.isPresent()) {
            ProjectEntity projectEntity = existingProject.get();
            projectEntity.setTitle(projectDTO.getTitle());
            projectEntity.setDetails(Base64.getDecoder().decode(projectDTO.getDetails())); // Convert Base64 String to byte[]

            if (projectDTO.getImage() != null) {
                byte[] imageBytes = Base64.getDecoder().decode(projectDTO.getImage()); // Convert Base64 String to byte[]
                projectEntity.setImage(imageBytes);
            }

            ProjectEntity updatedEntity = projectRepo.save(projectEntity);
            return convertEntityToDTO(updatedEntity);
        } else {
            throw new RuntimeException("Project not found");
        }
    }


    public ProjectDTO updateProjectDetails(UUID id, Map<String, Object> updates) {
        Optional<ProjectEntity> existingProject = projectRepo.findById(id);
        if (existingProject.isPresent()) {
            ProjectEntity projectEntity = existingProject.get();


            if (updates.containsKey("details")) {
                byte[] detailsBytes = (byte[]) updates.get("details");
                projectEntity.setDetails(detailsBytes);
            }
            if (updates.containsKey("title")) {
                projectEntity.setTitle((String) updates.get("title"));
            }
            if (updates.containsKey("image")) {
                byte[] imageBytes = (byte[]) updates.get("image");
                projectEntity.setImage(imageBytes);
            }

            ProjectEntity updatedEntity = projectRepo.save(projectEntity);
            return convertEntityToDTO(updatedEntity);
        } else {
            throw new RuntimeException("Project not found");
        }
    }


    private ProjectDTO convertEntityToDTO(ProjectEntity projectEntity) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setTitle(projectEntity.getTitle());

        if (projectEntity.getDetails() != null) {
            String base64Details = Base64.getEncoder().encodeToString(projectEntity.getDetails()); // Convert byte[] to Base64 String
            projectDTO.setDetails(base64Details);
        }

        if (projectEntity.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(projectEntity.getImage()); // Convert byte[] to Base64 String
            projectDTO.setImage(base64Image);
        }

        return projectDTO;
    }

    private ProjectEntity convertDTOToEntity(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setTitle(projectDTO.getTitle());

        if (projectDTO.getDetails() != null) {
            byte[] detailsBytes = Base64.getDecoder().decode(projectDTO.getDetails()); // Decode Base64 to byte[]
            projectEntity.setDetails(detailsBytes);
        }

        if (projectDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(projectDTO.getImage()); // Decode Base64 to byte[]
            projectEntity.setImage(imageBytes);
        }

        return projectEntity;
    }
}
