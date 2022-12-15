package ohgk.genesis.api.services.ddb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.config.SystemConfig;
import ohgk.genesis.api.entities.Project;
import ohgk.genesis.api.entities.ProjectLanguage;
import ohgk.genesis.api.entities.ProjectType;
import ohgk.genesis.api.enums.ProjectStatus;
import ohgk.genesis.api.exceptions.project.InvalidProjectException;
import ohgk.genesis.api.models.dto.ProjectDto;
import ohgk.genesis.api.services.ProjectService;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

@Slf4j
@SpringBootTest
public class DdbProjectServiceImplTest {

    private static final String SYSTEM_NAME = "ohgk-genesis";
    private static final BeanTableSchema<Project> PROJECT_TABLE_SCHEMA = TableSchema.fromBean(Project.class);
    private static Validator validator;

    @Mock private DynamoDbEnhancedClient ddbClient;
    @Mock private DynamoDbTable<Project> ddbTable;

    @Mock private SystemConfig systemConfig;

    private ProjectService projectService;

    private static Project expectedProject;

    @BeforeAll
    static void initAll() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        expectedProject = Project.builder()
            .id("test-id")
            .name("test-name")
            .description("test description")
            .languages(
                Arrays.asList(
                    ProjectLanguage.builder()
                        .id("JAVA")
                        .build()
                )
            )
            .type(
                ProjectType.builder()
                    .id("WEB_APP")
                    .build()
            )
            .status(ProjectStatus.TODO)
            .url("https://test-url.com")
            .build();
    }

    @BeforeEach
    void initEach() {

        // Test
        Key key = Key.builder()
            .partitionValue(expectedProject.getId())
            .build();

        GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
            .key(key)
            .build();

        when(systemConfig.getSystemName()).thenReturn(SYSTEM_NAME);
        when(ddbClient.table(
            String.format("%s-projects-table", SYSTEM_NAME), 
            PROJECT_TABLE_SCHEMA
        )).thenReturn(ddbTable);
        when(ddbTable.getItem(request)).thenReturn(expectedProject);

        this.projectService = new DdbProjectServiceImpl(
            systemConfig, 
            ddbClient,
            PROJECT_TABLE_SCHEMA,
            validator
        );
    }

    @Test
    public void getProjectById_ProjectExists_ReturnProject() {

        // Input
        String testId = expectedProject.getId();

        // Invoke
        ProjectDto result = this.projectService.getProjectById(testId);

        // Assert
        assertEquals(ProjectDto.fromEntity(expectedProject), result);
    }

    @Test
    public void getProjectById_ProjectNotExists_ReturnProject() {

        // Input
        String testId = "non-existent-id";

        // Invoke
        ProjectDto result = this.projectService.getProjectById(testId);

        // Assert
        assertNull(result);

    }

    @Test
    public void createProject_ValidProject_ReturnProjectId() throws InvalidProjectException {

        // Input
        ProjectDto input = ProjectDto.fromEntity(expectedProject);
        input.setId(null);

        // Invoke
        ProjectDto result = projectService.createProject(input);

        // Assert
        assertNotNull(result.getId());
    }

    @Test
    public void createProject_InvalidProject_ThrowInvalidProjectException() throws InvalidProjectException {

        // Input
        ProjectDto input = ProjectDto.fromEntity(expectedProject);
        input.setId(null);
        input.setName(null);

        // Assert
        var exception = assertThrows(InvalidProjectException.class, () -> projectService.createProject(input));
        assertTrue(exception.getViolations().contains("Name cannot be empty"));
    }
}
