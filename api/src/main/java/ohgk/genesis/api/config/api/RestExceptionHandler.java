package ohgk.genesis.api.config.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.enums.ResponseStatusEnum;
import ohgk.genesis.api.exceptions.project.InvalidProjectException;
import ohgk.genesis.api.models.http.ValidationErrorHttpResponse;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private static final String VALIDATION_ERROR_MESSAGE = "Validation error"; 

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleExceptions(Exception e){

        var ex = (IllegalArgumentException) e;

        return ResponseEntity.ok("puso");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleExceptions(InvalidProjectException ex){

        var response = ValidationErrorHttpResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(ResponseStatusEnum.FAILED.getValue())
            .message(ex.getMessage())
            .violations(ex.getViolations() == null ? null : new ArrayList<>(ex.getViolations()))
            .build();

        return ResponseEntity.ok(response);
    }

    // // Validation
    // @ExceptionHandler
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // private ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {

    //     var errors = ex.getAllErrors();

    //     var response = ValidationErrorHttpResponse.builder()
    //         .statusCode(HttpStatus.BAD_REQUEST.value())
    //         .status(ResponseStatusEnum.FAILED.getValue())
    //         .message(VALIDATION_ERROR_MESSAGE)
    //         .violations(
    //             errors.stream().map(
    //             (error) -> {
    //                 return error.getDefaultMessage();
    //             })
    //             .collect(Collectors.toList())
    //         )
    //         .build();

    //     return ResponseEntity.ok(response);
    // }
}
