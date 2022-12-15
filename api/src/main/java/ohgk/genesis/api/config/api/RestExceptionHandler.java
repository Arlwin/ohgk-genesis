package ohgk.genesis.api.config.api;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.enums.ResponseStatus;
import ohgk.genesis.api.models.http.ValidationErrorHttpResponse;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    private static final String VALIDATION_ERROR_MESSAGE = "Validation error"; 

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers, 
        HttpStatus status, 
        WebRequest request
    ) 
    {
        var errors = ex.getAllErrors();

        var response = ValidationErrorHttpResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(ResponseStatus.FAILED.getValue())
            .message(VALIDATION_ERROR_MESSAGE)
            .violations(
                errors.stream().map(
                (error) -> {
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList())
            )
            .build();

        // return super.handleMethodArgumentNotValid(ex, headers, status, request);
        return ResponseEntity.ok(response);
    }
    
    
}
