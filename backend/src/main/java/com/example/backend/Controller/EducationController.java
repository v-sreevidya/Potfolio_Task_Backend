package com.example.backend.Controller;

import com.example.backend.Entity.EducationEntity;
import com.example.backend.Service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/educations")
public class EducationController {
    @Autowired
    private EducationService educationService;

    @GetMapping("/get")
    public List<EducationEntity> getAllEducations() {
        return educationService.getAllEducations();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EducationEntity> getEducationById(@PathVariable UUID id) {
        Optional<EducationEntity> educationEntity = educationService.getEducationById(id);
        return educationEntity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/create")
    public ResponseEntity<EducationEntity> createEducation(@RequestBody EducationEntity educationEntity) {
        EducationEntity createdEducation = educationService.createEducation(educationEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEducation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EducationEntity> updateEducation(@PathVariable UUID id, @RequestBody EducationEntity educationEntity) {
        EducationEntity updatedEducation = educationService.updateEducation(id, educationEntity);
        return ResponseEntity.ok(updatedEducation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable UUID id) {
        educationService.deleteEducation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
