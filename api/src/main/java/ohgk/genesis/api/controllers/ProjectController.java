package ohgk.genesis.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.enums.ResponseStatus;
import ohgk.genesis.api.exceptions.project.InvalidProjectException;
import ohgk.genesis.api.models.dto.ProjectDto;
import ohgk.genesis.api.models.http.ProjectHttpResponse;
import ohgk.genesis.api.services.ProjectService;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(
        ProjectService projectService
    ) {

        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectHttpResponse> create(@Validated @RequestBody ProjectDto object) {

        ProjectHttpResponse response;
        
        try {

            ProjectDto projectResult = this.projectService.createProject(object);
            
            response = ProjectHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatus.SUCCESS.getValue())
                .message("Project successfully created.")
                .data(projectResult)
                .build();
        
        } catch (InvalidProjectException e) {

            response = ProjectHttpResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(ResponseStatus.FAILED.getValue())
                .message(e.getMessage())
                .build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectHttpResponse> getById(@PathVariable String id) {

        ProjectHttpResponse response;

        ProjectDto projectResult = this.projectService.getProjectById(id);

        if (projectResult == null) {

            response = ProjectHttpResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(ResponseStatus.FAILED.getValue())
                .message("Project does not exist.")
                .data(null)
                .build();
        } else {
        
            response = ProjectHttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(ResponseStatus.SUCCESS.getValue())
                .message("Project successfully fetched.")
                .data(projectResult)
                .build();
        }

        return ResponseEntity.ok(response);
    }
}
