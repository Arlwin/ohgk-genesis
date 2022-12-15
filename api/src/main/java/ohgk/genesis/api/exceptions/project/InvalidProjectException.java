package ohgk.genesis.api.exceptions.project;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import ohgk.genesis.api.models.dto.ProjectDto;

public class InvalidProjectException extends Exception {
    
    private static final String INVALID_PROJECT_DTO_MESSAGE = "ProjectDto is invalid with multiple violations";

    private Set<String> violations;

    private InvalidProjectException(String message, Set<String> violations) {

        super(message);
        this.violations = violations;
    }

    public Set<String> getViolations(){
        return this.violations;
    }

    public static InvalidProjectException invalidProjectDto(Set<ConstraintViolation<ProjectDto>> violations) {

        Set<String> violationsSet = violations.stream()
            .map(
                (violation) -> {
                    return violation.getMessage();
                }
            ).collect(Collectors.toSet());

        return new InvalidProjectException(
            INVALID_PROJECT_DTO_MESSAGE,
            violationsSet
        );
    }
}
