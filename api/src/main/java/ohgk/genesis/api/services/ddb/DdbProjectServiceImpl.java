package ohgk.genesis.api.services.ddb;

import java.util.UUID;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;
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
    public ProjectDto getProjectById(String id) {

        Key key = Key.builder()
            .partitionValue(id)
            .build();

        var project = this.ddbTable.getItem(
            GetItemEnhancedRequest.builder()
                .key(key)
                .build()
        );

        if (project == null) return null;

        return ProjectDto.fromEntity(project);
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
}
