package com.freelance.backend.controller;

import com.freelance.backend.dto.ProjectRequest;
import com.freelance.backend.dto.ProjectResponse;
import com.freelance.backend.entity.User;
import com.freelance.backend.repository.UserRepository;
import com.freelance.backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService, UserRepository userRepository) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request, Authentication authentication) {
        User client = (User) authentication.getPrincipal();
        ProjectResponse response =
                projectService.createProject(request, client);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProjectResponse>> getMyProjects(Authentication authentication) {
        User client = (User) authentication.getPrincipal();

        List<ProjectResponse> projects =
                projectService.getMyProjects(client);

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        ProjectResponse response =
                projectService.getProjectById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest request, Authentication authentication) {
        User client = (User) authentication.getPrincipal();

        ProjectResponse response =
                projectService.updateProject(id, request, client);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id, Authentication authentication) {
        User client = (User) authentication.getPrincipal();

        projectService.deleteProject(id, client);

        return ResponseEntity.noContent().build();
    }
}