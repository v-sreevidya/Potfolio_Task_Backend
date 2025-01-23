package com.example.backend.Controller;

import com.example.backend.DTO.ProjectDTO;

import com.example.backend.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @PostMapping
    public ProjectDTO saveProjectDTO(@RequestBody ProjectDTO projectDTO){
        return projectService.saveProject(projectDTO);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable UUID id){
        return projectService.getProjectById(id);
    }
}
