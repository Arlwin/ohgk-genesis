package ohgk.genesis.api.exceptions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import ohgk.genesis.api.models.dto.ProjectDto;

public class InvalidProjectException extends Exception {
    
    private static final String INVALID_PROJECT_DTO_MESSAGE = "Project is invalid with multiple violations";
    private static final String INVALID_FIELD_VALUE_MESSAGE = "Field %s does not allow %s. Supported values are %s";
    private static final String NULL_FIELD_VALUE_MESSAGE = "Field %s should not be null";
    private static final String PROJECT_NOT_EXIST = "Project with id %s does not exist";

    private Set<String> violations;

    private InvalidProjectException(String message, Set<String> violations) {

        super(message);
        this.violations = violations;
    }

    private InvalidProjectException(String message) {

        super(message);
    }

    public Set<String> getViolations(){
        return this.violations;
    }

    public static InvalidProjectException projectDoesNotExist(String id) {

        return new InvalidProjectException(String.format(PROJECT_NOT_EXIST, id));
    }

    public static InvalidProjectException fieldValueIsNull(String field) {

        return new InvalidProjectException(String.format(NULL_FIELD_VALUE_MESSAGE, field));
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

    public static InvalidProjectException invalidFieldValue(String fieldName, Set<String> allowedValues, String value) {
        
        return new InvalidProjectException(
            String.format(
                INVALID_FIELD_VALUE_MESSAGE, 
                fieldName,
                value,
                allowedValues.toString()    
            )
        );
    }
}
