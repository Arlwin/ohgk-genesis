package ohgk.genesis.api.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.enums.ProjectStatusEnum;
import ohgk.genesis.api.enums.ProjectTypeEnum;
import ohgk.genesis.api.enums.ResponseStatusEnum;
import ohgk.genesis.api.exceptions.InvalidProjectException;
import ohgk.genesis.api.models.dto.ProjectDto;
import ohgk.genesis.api.models.http.BaseHttpResponse;
import ohgk.genesis.api.services.ProjectService;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectService projectService;
    private ObjectMapper objectMapper;

    @Autowired
    public ProjectController(
        ProjectService projectService,
        ObjectMapper objectMapper
    ) {

        this.projectService = projectService;
        this.objectMapper = objectMapper;
    }

    // Utilities
    @GetMapping("/types")
    public ResponseEntity<BaseHttpResponse> getProjectTypes() {

        Map<String, String> types = Arrays.stream(ProjectTypeEnum.values())
            .collect(Collectors.toMap((type) -> type.name(), (type) -> type.getLabel()));

        return ResponseEntity.ok(
            BaseHttpResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .status(ResponseStatusEnum.SUCCESS.getValue())
            .message("Project types successfully fetched.")
            .data(objectMapper.valueToTree(types))
            .build()
        );
    }

    @GetMapping("/status")
    public ResponseEntity<BaseHttpResponse> getProjectStatus() {

        Map<String, String> status = Arrays.stream(ProjectStatusEnum.values())
            .collect(Collectors.toMap((type) -> type.name(), (type) -> type.getLabel()));

        return ResponseEntity.ok(
            BaseHttpResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .status(ResponseStatusEnum.SUCCESS.getValue())
            .message("Project status successfully fetched.")
            .data(objectMapper.valueToTree(status))
            .build()
        );
    }

    @PostMapping
    public ResponseEntity<BaseHttpResponse> create(@RequestBody ProjectDto project, Authentication auth) throws InvalidProjectException {

        BaseHttpResponse response;

        var user = (UserDetails) auth.getPrincipal();
        project.setOwner(user.getUsername());
        
        ProjectDto projectResult = this.projectService.createProject(project);
        
        response = BaseHttpResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .status(ResponseStatusEnum.SUCCESS.getValue())
            .message("Project successfully created.")
            .data(objectMapper.valueToTree(projectResult))
            .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseHttpResponse> getById(@PathVariable String id) {

        BaseHttpResponse response;

        ProjectDto projectResult = this.projectService.getProjectById(id);

        if (projectResult == null) {

            response = BaseHttpResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(ResponseStatusEnum.FAILED.getValue())
                .message("Project does not exist.")
                .data(null)
                .build();
        } else {
        
            response = BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.getValue())
                .message("Project successfully fetched.")
                .data(objectMapper.valueToTree(projectResult))
                .build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseHttpResponse> getAll(@RequestParam Optional<String> user) {

        List<ProjectDto> projects;

        if (user.isPresent() && !user.get().isEmpty()) 
            projects = this.projectService.getProjectsByUser(user.get());
        else 
            projects = this.projectService.getProjects();
            
        BaseHttpResponse baseResponse = BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.getValue())
                .message("Successfully got project list.")
                .data(projects.isEmpty() ? new ArrayNode(null) : this.objectMapper.valueToTree(projects))
                .build();

        return ResponseEntity.ok(baseResponse);
    }   

    @PutMapping
    public ResponseEntity<BaseHttpResponse> update(@RequestBody ProjectDto project) throws InvalidProjectException {
        
        ProjectDto projectResult = this.projectService.updateProject(project);
        
        BaseHttpResponse response = BaseHttpResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .status(ResponseStatusEnum.SUCCESS.getValue())
            .message("Project successfully updated.")
            .data(objectMapper.valueToTree(projectResult))
            .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseHttpResponse> delete(@PathVariable String id) throws InvalidProjectException {

        BaseHttpResponse response;
        
        ProjectDto projectResult = this.projectService.deleteProjectById(id);
        
        response = BaseHttpResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .status(ResponseStatusEnum.SUCCESS.getValue())
            .message("Project successfully deleted.")
            .data(objectMapper.valueToTree(projectResult))
            .build();

        return ResponseEntity.ok(response);
    }
}
