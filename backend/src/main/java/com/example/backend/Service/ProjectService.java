package com.example.backend.Service;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.Entity.ProjectEntity;
import com.example.backend.Repository.ProjectRepo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }


    private String extractTextFromPdf(byte[] pdfBytes) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes);
             PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }


    public ProjectDTO getProjectById(UUID id) {
        ProjectEntity projectEntity = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        String detailsText = null;
        if (projectEntity.getDetails() != null) {
            try {
                detailsText = extractTextFromPdf(projectEntity.getDetails());
            } catch (IOException e) {
                throw new RuntimeException("Error extracting PDF text", e);
            }
        }

        return convertEntityToDTO(projectEntity, detailsText);
    }

    private ProjectDTO convertEntityToDTO(ProjectEntity projectEntity, String detailsText) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setTitle(projectEntity.getTitle());
        projectDTO.setDetails(detailsText);

        if (projectEntity.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(projectEntity.getImage());
            projectDTO.setImage(base64Image);
        }

        return projectDTO;
    }


    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = convertDTOToEntity(projectDTO);
        ProjectEntity savedEntity = projectRepo.save(projectEntity);
        return convertEntityToDTO(savedEntity, null);
    }

    private ProjectEntity convertDTOToEntity(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setTitle(projectDTO.getTitle());
        projectEntity.setDetails(Base64.getDecoder().decode(projectDTO.getDetails())); // Decode Base64 for PDF

        if (projectDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(projectDTO.getImage());
            projectEntity.setImage(imageBytes);
        }

        return projectEntity;
    }

    public ProjectDTO updateProject(UUID id, ProjectDTO projectDTO) {
        return projectDTO;
    }
}
