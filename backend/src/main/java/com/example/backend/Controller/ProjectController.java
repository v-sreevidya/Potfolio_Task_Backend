package com.example.backend.Controller;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.DTO.Response;
import com.example.backend.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
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
            @RequestPart("details") String details,

            @RequestPart("image") MultipartFile image) {

        try {
            System.out.println("Title: " + title);
            System.out.println("Details: " + details);

            System.out.println("Image: " + image.getOriginalFilename());
            byte[] imageBytes = image.getBytes();

            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setTitle(title);
            projectDTO.setDetails(details);

            projectDTO.setImage(Base64.getEncoder().encodeToString(imageBytes));

            ProjectDTO savedProject = projectService.saveProject(projectDTO);


            return ResponseEntity.ok(savedProject);
        } catch (IOException e) {


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }



    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable UUID id) {
        try {
            ProjectDTO projectDTO = projectService.getProjectById(id);
            return ResponseEntity.ok(projectDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProjectById(
            @PathVariable UUID id,
            @RequestPart("title") String title,
            @RequestPart("details") String details,
            @RequestPart("image") MultipartFile image) {

        try {
            System.out.println("Updating project with ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Details: " + details);

            // Convert image to base64
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setTitle(title);
            projectDTO.setDetails(details);
            projectDTO.setImage(Base64.getEncoder().encodeToString(image.getBytes()));

            // Call service to update the project
            ProjectDTO updatedProjectDTO = projectService.updateProjectById(id, projectDTO);
            return ResponseEntity.ok(updatedProjectDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}


