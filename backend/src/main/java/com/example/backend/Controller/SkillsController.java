package com.example.backend.Controller;

import com.example.backend.DTO.SkillsDTO;
import com.example.backend.Service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/skills")
@CrossOrigin(origins = "http://localhost:3000")
public class SkillsController {

    @Autowired
    private SkillsService skillsService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<SkillsDTO> saveSkillsDTO(
            @RequestPart("title") String title,
            @RequestPart("image") MultipartFile image) {

        try {
            System.out.println("Title: " + title);
            System.out.println("Image: " + image.getOriginalFilename());
            byte[] imageBytes = image.getBytes();

            SkillsDTO skillsDTO = new SkillsDTO();
            skillsDTO.setTitle(title);
            skillsDTO.setImage(Base64.getEncoder().encodeToString(imageBytes));

            SkillsDTO savedSkills = skillsService.saveSkills(skillsDTO);

            return ResponseEntity.ok(savedSkills);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<SkillsDTO>> getAllSkills() {
        try {
            List<SkillsDTO> skills = skillsService.getAllSkills();

            return ResponseEntity.ok(skills);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SkillsDTO> getSkillById(@PathVariable UUID id) {
        try {
            SkillsDTO skillsDTO = skillsService.getSkillsById(id);
            return ResponseEntity.ok(skillsDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillsDTO> updateSkillById(
            @PathVariable UUID id,
            @RequestPart("title") String title,
            @RequestPart("image") MultipartFile image) {

        try {
            System.out.println("Updating skill with ID: " + id);
            System.out.println("Title: " + title);

            // Convert image to base64
            SkillsDTO skillsDTO = new SkillsDTO();
            skillsDTO.setTitle(title);
            skillsDTO.setImage(Base64.getEncoder().encodeToString(image.getBytes()));

            // Call service to update the skill
            SkillsDTO updatedSkillsDTO = skillsService.updateSkillsById(id, skillsDTO);
            return ResponseEntity.ok(updatedSkillsDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteSkillById(@PathVariable UUID id) {
        try {
            skillsService.deleteSkillsById(id);
            return ResponseEntity.ok("Skill deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Skill with ID " + id + " not found.");
        }
    }
}
