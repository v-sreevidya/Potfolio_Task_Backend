package com.example.backend.Controller;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/projects")
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProjectDTO> saveProjectDTO(
            @RequestPart("title") String title,
            @RequestPart("details") MultipartFile detailsFile, // Updated to accept file
            @RequestPart("image") MultipartFile image) {
        try {
            byte[] imageBytes = image.getBytes();
            byte[] detailsBytes = detailsFile.getBytes(); // Get file content for details

            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setTitle(title);
            projectDTO.setDetails(Base64.getEncoder().encodeToString(detailsBytes)); // Store as Base64 String
            projectDTO.setImage(Base64.getEncoder().encodeToString(imageBytes));

            ProjectDTO savedProject = projectService.saveProject(projectDTO);
            return ResponseEntity.ok(savedProject);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable UUID id) {
        try {
            ProjectDTO projectDTO = projectService.getProjectById(id);
            return ResponseEntity.ok(projectDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProjectDTO> updateProjectDetails(
            @PathVariable UUID id,
            @RequestPart(value = "details", required = false) MultipartFile detailsFile, // Accept file (optional)
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {

            ProjectDTO projectDTO = projectService.getProjectById(id);


            if (detailsFile != null && !detailsFile.isEmpty()) {
                byte[] detailsBytes = detailsFile.getBytes();
                projectDTO.setDetails(Base64.getEncoder().encodeToString(detailsBytes)); // Store as Base64
            }


            if (title != null && !title.isEmpty()) {
                projectDTO.setTitle(title);
            }


            if (image != null && !image.isEmpty()) {
                byte[] imageBytes = image.getBytes();
                projectDTO.setImage(Base64.getEncoder().encodeToString(imageBytes));
            }

            ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
            return ResponseEntity.ok(updatedProject);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable UUID id,
            @RequestPart("title") String title,
            @RequestPart("details") MultipartFile detailsFile, // Accept file for details
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            byte[] detailsBytes = detailsFile.getBytes(); // Get file content for details
            byte[] imageBytes = (image != null && !image.isEmpty()) ? image.getBytes() : null;

            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(id);
            projectDTO.setTitle(title);
            projectDTO.setDetails(Base64.getEncoder().encodeToString(detailsBytes)); // Store as Base64 String

            if (imageBytes != null) {
                projectDTO.setImage(Base64.getEncoder().encodeToString(imageBytes));
            }

            ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
            return ResponseEntity.ok(updatedProject);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
