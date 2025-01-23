package com.example.backend.Service;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.Entity.ProjectEntity;
import com.example.backend.Repository.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;
@Service
public class ProjectService {
    private ProjectRepo projectRepo;
    public ProjectDTO getProjectById(UUID id){
        ProjectEntity projectEntity=projectRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Project not found"));
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setTitle(projectEntity.getTitle());
        projectDTO.setDetails(projectEntity.getDetails());
        if (projectEntity.getImage() !=null){
            String base64Image = Base64.getEncoder().encodeToString(projectEntity.getImage());
            projectDTO.setIamge(base64Image);
        }
        return projectDTO;
    }

    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        return projectDTO;
    }
}
