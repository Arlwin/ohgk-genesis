package ohgk.genesis.api.config.api;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.enums.ResponseStatusEnum;
import ohgk.genesis.api.exceptions.InvalidProjectException;
import ohgk.genesis.api.exceptions.InvalidUserException;
import ohgk.genesis.api.models.http.ValidationErrorHttpResponse;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleExceptions(Exception e){

        log.error(e.getMessage());

        return ResponseEntity.ok("puso");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleProjectExceptions(InvalidProjectException ex){

        var response = ValidationErrorHttpResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(ResponseStatusEnum.FAILED.getValue())
            .message(ex.getMessage())
            .violations(ex.getViolations() == null ? null : new ArrayList<>(ex.getViolations()))
            .build();

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleUserExceptions(InvalidUserException ex){

        var response = ValidationErrorHttpResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(ResponseStatusEnum.FAILED.getValue())
            .message(ex.getMessage())
            .violations(ex.getViolations() == null ? null : new ArrayList<>(ex.getViolations()))
            .build();

        return ResponseEntity.ok(response);
    }
}
