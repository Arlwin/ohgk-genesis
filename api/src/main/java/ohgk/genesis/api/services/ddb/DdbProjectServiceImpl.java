package ohgk.genesis.api.services.ddb;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.config.SystemConfig;
import ohgk.genesis.api.entities.Project;
import ohgk.genesis.api.exceptions.project.InvalidProjectException;
import ohgk.genesis.api.models.dto.ProjectDto;
import ohgk.genesis.api.services.ProjectService;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

@Slf4j
@Service("ddbProjectService")
public class DdbProjectServiceImpl implements ProjectService {

    private Validator validator;
    private DynamoDbTable<Project> ddbTable;
    
    @Autowired
    public DdbProjectServiceImpl(
        SystemConfig config,
        DynamoDbEnhancedClient dynamoDbEnhancedClient,
        BeanTableSchema<Project> getProjectTableSchema,
        Validator validator
    ){
        this.validator = validator;

        String tableName = String.format("%s-%s-table",
            config.getSystemName(),
            Project.TABLE_NAME
        );

        this.ddbTable = dynamoDbEnhancedClient.table(
            tableName, 
            getProjectTableSchema
        );
    }

    
    @Override
    public ProjectDto createProject(ProjectDto project) throws InvalidProjectException {

        var violations = validator.validate(project);

        if (violations.size() != 0) {

            throw InvalidProjectException.invalidProjectDto(violations);
        }

        String id = UUID.randomUUID().toString();

        project.setId(id);
        this.ddbTable.putItem(project.toEntity());

        return project;
    }

    @Override
    public ProjectDto getProjectById(String id) {

        Key key = Key.builder()
            .partitionValue(id)
            .build();

        var project = this.ddbTable.getItem(key);

        if (project == null) return null;

        return ProjectDto.fromEntity(project);
    }

    @Override
    public List<ProjectDto> getProjects() {

        List<ProjectDto> projects = this.ddbTable.scan().items().stream()
            .map(
                (item) -> {
                    return ProjectDto.fromEntity(item);
                }
            ).collect(Collectors.toList());

        return projects;
    }

    @Override
    public ProjectDto updateProject(ProjectDto project) throws InvalidProjectException {

        if (project.getId() == null) throw InvalidProjectException.fieldValueIsNull("id");

        var violations = validator.validate(project);

        if (violations.size() != 0) {

            throw InvalidProjectException.invalidProjectDto(violations);
        }

        this.ddbTable.putItem(project.toEntity());

        return project;
    }

    @Override
    public ProjectDto deleteProjectById(String id) throws InvalidProjectException {

        Key key = Key.builder()
            .partitionValue(id)
            .build();

        var result = this.ddbTable.deleteItem(key);

        if (result == null) throw InvalidProjectException.projectDoesNotExist(id);

        return ProjectDto.fromEntity(result);
    }
}
