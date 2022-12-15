package ohgk.genesis.api.services;

import ohgk.genesis.api.exceptions.project.InvalidProjectException;
import ohgk.genesis.api.models.dto.ProjectDto;

public interface ProjectService {
    
    public ProjectDto createProject(ProjectDto project) throws InvalidProjectException; // C
    public ProjectDto getProjectById(String id); // R
}
