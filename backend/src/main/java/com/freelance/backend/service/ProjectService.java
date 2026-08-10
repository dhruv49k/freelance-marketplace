package com.freelance.backend.service;

import com.freelance.backend.dto.ProjectRequest;
import com.freelance.backend.dto.ProjectResponse;
import com.freelance.backend.entity.ProjectStatus;
import com.freelance.backend.entity.User;
import com.freelance.backend.entity.Project;
import com.freelance.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest request, User client) {
        Project project = new Project();

        project.setClient(client);
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setBudget(request.getBudget());
        project.setStatus(ProjectStatus.OPEN);
        Project savedProject = projectRepository.save(project);
        return mapToResponse(savedProject);
    }

    private ProjectResponse mapToResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setClientId(project.getClient().getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setBudget(project.getBudget());
        response.setStatus(project.getStatus());
        response.setCreatedAt(project.getCreatedAt());

        return response;
    }

    public ProjectResponse getProjectById(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return mapToResponse(project);
    }

    public List<ProjectResponse> getMyProjects(User client) {

        List<Project> projects =
                projectRepository.findByClientId(client.getId());

        return projects.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateOwnership(Project project, User client) {

        if (!project.getClient().getId().equals(client.getId())) {
            throw new RuntimeException(
                    "You are not authorized to modify this project"
            );
        }
    }

    public ProjectResponse updateProject(
            Long projectId,
            ProjectRequest request,
            User client
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        validateOwnership(project, client);

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setBudget(request.getBudget());

        Project updatedProject = projectRepository.save(project);

        return mapToResponse(updatedProject);
    }

    public void deleteProject(Long projectId, User client) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        validateOwnership(project, client);

        projectRepository.delete(project);
    }
}