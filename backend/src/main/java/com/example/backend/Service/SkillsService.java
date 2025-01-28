package com.example.backend.Service;

import com.example.backend.DTO.SkillsDTO;
import com.example.backend.Entity.SkillsEntity;
import com.example.backend.Repository.SkillsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillsService {
    private final SkillsRepo skillsRepo;

    @Autowired
    public SkillsService(SkillsRepo skillsRepo) {
        this.skillsRepo = skillsRepo;
    }

    public SkillsDTO getSkillsById(UUID id) {
        SkillsEntity skillsEntity = skillsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        return convertEntityToDTO(skillsEntity);
    }

    public SkillsDTO mapToDTO(SkillsEntity skillsEntity) {
        SkillsDTO dto = new SkillsDTO();
        dto.setTitle(skillsEntity.getTitle());

        // Convert byte[] to Base64 String
        if (skillsEntity.getImage() != null) {
            dto.setImage(Base64.getEncoder().encodeToString(skillsEntity.getImage()));
        }

        return dto;
    }

    private SkillsDTO convertEntityToDTO(SkillsEntity skillsEntity) {
        SkillsDTO skillsDTO = new SkillsDTO();
        skillsDTO.setId(skillsEntity.getId());
        skillsDTO.setTitle(skillsEntity.getTitle());

        // Convert byte[] to Base64 String before setting in DTO
        if (skillsEntity.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(skillsEntity.getImage()); // Convert image to base64
            skillsDTO.setImage(base64Image); // Set the Base64 string in DTO
        }

        return skillsDTO;
    }

    public SkillsDTO updateSkillsById(UUID id, SkillsDTO skillsDTO) {
        // Find the existing skill
        SkillsEntity existingSkills = skillsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + id));
        System.out.println("Existing Skill before update: " + existingSkills);

        // Update fields
        if (skillsDTO.getTitle() != null) {
            existingSkills.setTitle(skillsDTO.getTitle());
        }

        if (skillsDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(skillsDTO.getImage());
            existingSkills.setImage(imageBytes);
        }

        System.out.println("Existing Skill after update: " + existingSkills);
        // Save the updated entity and return the updated DTO
        SkillsEntity updatedEntity = skillsRepo.save(existingSkills);
        return convertEntityToDTO(updatedEntity);
    }

    public List<SkillsDTO> getAllSkills() {
        return skillsRepo.findAll().stream()
                .map(skill -> {
                    SkillsDTO dto = new SkillsDTO();
                    dto.setId(skill.getId());
                    dto.setTitle(skill.getTitle());

                    dto.setImage(Arrays.toString(skill.getImage()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public SkillsDTO saveSkills(SkillsDTO skillsDTO) {
        SkillsEntity skillsEntity = convertDTOToEntity(skillsDTO);
        SkillsEntity savedEntity = skillsRepo.save(skillsEntity);
        return convertEntityToDTO(savedEntity);
    }

    private SkillsEntity convertDTOToEntity(SkillsDTO skillsDTO) {
        SkillsEntity skillsEntity = new SkillsEntity();
        skillsEntity.setTitle(skillsDTO.getTitle());

        if (skillsDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(skillsDTO.getImage());
            skillsEntity.setImage(imageBytes);
        }

        return skillsEntity;
    }

    public void deleteSkillsById(UUID id) {
        if (!skillsRepo.existsById(id)) {
            throw new RuntimeException("Skill not found");
        }
        skillsRepo.deleteById(id);
    }
}
