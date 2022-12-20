package ohgk.genesis.api.models.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.entities.Project;
import ohgk.genesis.api.enums.ProjectStatusEnum;
import ohgk.genesis.api.enums.ProjectTypeEnum;
import ohgk.genesis.api.exceptions.project.InvalidProjectException;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private static final Set<String> validTypes;
    private static final Set<String> validStatus;

    static {

        validTypes = Arrays.stream(ProjectTypeEnum.values())
            .map(
                (value) -> { return value.name(); }
            ).collect(Collectors.toSet());

            validStatus = Arrays.stream(ProjectStatusEnum.values())
            .map(
                (value) -> { return value.name(); }
            ).collect(Collectors.toSet());
    }
    
    private String id;
    
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Should have at least one language")
    private List<String> languages;

    @NotNull(message = "Must have a type")
    private String type;

    @NotNull(message = "Must have a status")
    private String status;

    private String url;

    public static ProjectDto fromEntity(Project entity){

        return new ProjectDto(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getLanguages(),
            entity.getType().toString(),
            entity.getStatus().toString(),
            entity.getUrl()
        );
    }

    public Project toEntity() throws InvalidProjectException {

        // Valid Type
        if (!validTypes.contains(this.type)) {

            throw InvalidProjectException.invalidFieldValue(
                "type", 
                validTypes, 
                this.type
            );
        }

        // Valid Status
        if (!validStatus.contains(this.status)) {

            throw InvalidProjectException.invalidFieldValue(
                "status", 
                validStatus, 
                this.status
            );
        }

        return Project.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .languages(this.languages)
            .type(ProjectTypeEnum.valueOf(this.type))
            .status(ProjectStatusEnum.valueOf(this.status))
            .url(this.url)
            .build();

    }
}
