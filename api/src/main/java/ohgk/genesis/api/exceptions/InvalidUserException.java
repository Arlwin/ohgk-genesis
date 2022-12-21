package ohgk.genesis.api.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import ohgk.genesis.api.models.dto.UserDto;

public class InvalidUserException extends Exception {

    private static final String INVALID_USER_DTO_MESSAGE = "User is invalid with multiple violations";
    private static final String USER_NOT_EXIST = "User with username %s does not exist";
    private static final String USER_ALREADY_EXIST = "User with username %s already exists";

    private Set<String> violations;
    
    private InvalidUserException(String message) {
        super(message);
    }

    private InvalidUserException(String message, Set<String> violations) {

        super(message);
        this.violations = violations;
    }

    public Set<String> getViolations(){
        return this.violations;
    }

    public static InvalidUserException invalidUserDto(Set<ConstraintViolation<UserDto>> violations) {

        Set<String> violationsSet = violations.stream()
            .map(
                (violation) -> {
                    return violation.getMessage();
                }
            ).collect(Collectors.toSet());

        return new InvalidUserException(
            INVALID_USER_DTO_MESSAGE,
            violationsSet
        );
    }
    
    public static InvalidUserException userDoesNotExist(String username) {

        return new InvalidUserException(String.format(USER_NOT_EXIST, username));
    }
    
    public static InvalidUserException userAlreadyExists(String username) {

        return new InvalidUserException(String.format(USER_ALREADY_EXIST, username));
    }
}
