package ohgk.genesis.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ohgk.genesis.api.enums.ResponseStatusEnum;
import ohgk.genesis.api.exceptions.InvalidUserException;
import ohgk.genesis.api.models.dto.UserDto;
import ohgk.genesis.api.models.http.BaseHttpResponse;
import ohgk.genesis.api.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(
        UserService userService,
        ObjectMapper objectMapper
    ){
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    // The public APIs
    @PostMapping("/signUp")
    public ResponseEntity<BaseHttpResponse> signUp(@RequestBody UserDto user) throws InvalidUserException {

        UserDto result = this.userService.createUser(user, true);

        return ResponseEntity.ok(
            BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.name())
                .message("User successfully created.")
                .data(this.objectMapper.valueToTree(result))
                .build()
        );
    }

    // The Usual CRUD. For Admin only
    @PostMapping
    public ResponseEntity<BaseHttpResponse> create(@RequestBody UserDto user) throws InvalidUserException {

        UserDto result = this.userService.createUser(user, false);

        return ResponseEntity.ok(
            BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.name())
                .message("User successfully created.")
                .data(this.objectMapper.valueToTree(result))
                .build()
        );
    }
     
    @GetMapping
    public ResponseEntity<BaseHttpResponse> getAll() {

        List<UserDto> users = this.userService.getUsers();

        BaseHttpResponse baseResponse = BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.getValue())
                .message("Successfully got user list.")
                .data(this.objectMapper.valueToTree(users))
                .build();

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{username}")
    public ResponseEntity<BaseHttpResponse> getById(@PathVariable String username) throws InvalidUserException {

        UserDto user = this.userService.getUserByUsername(username);

        BaseHttpResponse baseResponse = BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.getValue())
                .message("Successfully fetched user.")
                .data(this.objectMapper.valueToTree(user))
                .build();

        return ResponseEntity.ok(baseResponse);
    }
    
    @PutMapping
    public ResponseEntity<BaseHttpResponse> update(@RequestBody UserDto user) throws InvalidUserException {

        UserDto result = this.userService.updateUser(user);

        return ResponseEntity.ok(
            BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.name())
                .message("User successfully updated.")
                .data(this.objectMapper.valueToTree(result))
                .build()
        );
    }
    
    @DeleteMapping("/{username}")
    public ResponseEntity<BaseHttpResponse> delete(@PathVariable String username) throws InvalidUserException {

        UserDto result = this.userService.deleteUserByUsername(username);
        
        BaseHttpResponse response = BaseHttpResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .status(ResponseStatusEnum.SUCCESS.getValue())
            .message("User successfully deleted.")
            .data(objectMapper.valueToTree(result))
            .build();

        return ResponseEntity.ok(response);
    }
}
