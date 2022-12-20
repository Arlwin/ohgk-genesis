package ohgk.genesis.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.enums.ResponseStatusEnum;
import ohgk.genesis.api.exceptions.project.InvalidProjectException;
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

    @PostMapping
    public ResponseEntity<BaseHttpResponse> create(@RequestBody ProjectDto project) throws InvalidProjectException {

        BaseHttpResponse response;
        
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
    public ResponseEntity<ObjectNode> getAll(){
        
        ObjectNode baseResponse;

        List<ProjectDto> projects = this.projectService.getProjects();

        baseResponse = (ObjectNode) this.objectMapper.valueToTree(
            BaseHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatusEnum.SUCCESS.getValue())
                .message("Successfully got project list.")
                .build()
        );

        baseResponse.set("data", this.objectMapper.valueToTree(projects));

        return ResponseEntity.ok(baseResponse);
    }   

    @PutMapping
    public ResponseEntity<BaseHttpResponse> update(@RequestBody ProjectDto project) throws InvalidProjectException {
        
        BaseHttpResponse response;
        
        ProjectDto projectResult = this.projectService.updateProject(project);
        
        response = BaseHttpResponse.builder()
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
