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


        if (skillsEntity.getImage() != null) {
            dto.setImage(Base64.getEncoder().encodeToString(skillsEntity.getImage()));
        }

        return dto;
    }

    private SkillsDTO convertEntityToDTO(SkillsEntity skillsEntity) {
        SkillsDTO skillsDTO = new SkillsDTO();
        skillsDTO.setId(skillsEntity.getId());
        skillsDTO.setTitle(skillsEntity.getTitle());


        if (skillsEntity.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(skillsEntity.getImage());
            skillsDTO.setImage(base64Image);
        }

        return skillsDTO;
    }

    public SkillsDTO updateSkillsById(UUID id, SkillsDTO skillsDTO) {

        SkillsEntity existingSkills = skillsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + id));
        System.out.println("Existing Skill before update: " + existingSkills);


        if (skillsDTO.getTitle() != null) {
            existingSkills.setTitle(skillsDTO.getTitle());
        }

        if (skillsDTO.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(skillsDTO.getImage());
            existingSkills.setImage(imageBytes);
        }

        System.out.println("Existing Skill after update: " + existingSkills);

        SkillsEntity updatedEntity = skillsRepo.save(existingSkills);
        return convertEntityToDTO(updatedEntity);
    }

    public List<SkillsDTO> getAllSkills() {
        return skillsRepo.findAll().stream()
                .map(skill -> {
                    SkillsDTO dto = new SkillsDTO();
                    dto.setId(skill.getId());
                    dto.setTitle(skill.getTitle());


                    if (skill.getImage() != null) {
                        dto.setImage(Base64.getEncoder().encodeToString(skill.getImage()));
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


    public SkillsDTO saveSkills(SkillsDTO skillsDTO) {
        Optional<SkillsEntity> existingSkill = skillsRepo.findByTitleIgnoreCase(skillsDTO.getTitle());

        if (existingSkill.isPresent()) {
            throw new RuntimeException("A skill with this title already exists.");
        }
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
