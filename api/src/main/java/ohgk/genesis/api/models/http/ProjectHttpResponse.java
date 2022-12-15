package ohgk.genesis.api.models.http;

import lombok.experimental.SuperBuilder;
import ohgk.genesis.api.models.dto.ProjectDto;

@SuperBuilder
public class ProjectHttpResponse extends BaseHttpResponse {
    
    private ProjectDto data;
}
