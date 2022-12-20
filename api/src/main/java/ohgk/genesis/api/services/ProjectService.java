package ohgk.genesis.api.services;

import java.util.List;

import ohgk.genesis.api.exceptions.project.InvalidProjectException;
import ohgk.genesis.api.models.dto.ProjectDto;

public interface ProjectService {
    
    // Create
    public ProjectDto createProject(ProjectDto project) throws InvalidProjectException; 

    // Read
    public List<ProjectDto> getProjects();
    public ProjectDto getProjectById(String id);

    // Update
    public ProjectDto updateProject(ProjectDto project) throws InvalidProjectException;

    // Delete
    public ProjectDto deleteProjectById(String id) throws InvalidProjectException;
}
