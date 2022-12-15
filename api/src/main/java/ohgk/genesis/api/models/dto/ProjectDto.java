package ohgk.genesis.api.models.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohgk.genesis.api.entities.Project;
import ohgk.genesis.api.entities.ProjectLanguage;
import ohgk.genesis.api.entities.ProjectType;
import ohgk.genesis.api.enums.ProjectStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    
    private String id;
    
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Should have at least one language")
    private List<String> languages;

    @NotEmpty(message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Must have a status")
    private ProjectStatus status;

    private String url;

    public static ProjectDto fromEntity(Project entity){

        var languages = entity.getLanguages()
            .stream()
            .map(
                (language) -> {
                    return language.getId();
                }
            )
            .collect(Collectors.toList());

        return new ProjectDto(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            languages,
            entity.getType().getId(),
            entity.getStatus(),
            entity.getUrl()
        );
    }

    public Project toEntity() {

        var projLanguages = this.languages
            .stream()
            .map(
                (language) -> {
                    return ProjectLanguage.builder()
                        .id(language)
                        .build();
                }
            )
            .collect(Collectors.toList());

        return Project.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .languages(projLanguages)
            .type(
                ProjectType.builder()
                    .id(this.type)
                    .build()
            )
            .status(this.status)
            .url(this.url)
            .build();
    }
}
