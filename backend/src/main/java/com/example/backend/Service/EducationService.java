package com.example.backend.Service;

import com.example.backend.Entity.EducationEntity;
import com.example.backend.Repository.EducationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EducationService {
    @Autowired
    private EducationRepo educationRepo;

    public List<EducationEntity> getAllEducations() {
        return educationRepo.findAll();
    }

    public Optional<EducationEntity> getEducationById(UUID id) {
        return educationRepo.findById(id);
    }

    public EducationEntity createEducation(EducationEntity educationEntity) {
        return educationRepo.save(educationEntity);
    }

    public EducationEntity updateEducation(UUID id, EducationEntity educationEntity) {
        educationEntity.setId(id);
        return educationRepo.save(educationEntity);
    }

    public void deleteEducation(UUID id) {
        educationRepo.deleteById(id);
    }

}
