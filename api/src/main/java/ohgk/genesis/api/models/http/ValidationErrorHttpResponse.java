package ohgk.genesis.api.models.http;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class ValidationErrorHttpResponse extends BaseHttpResponse {
    
    private List<String> violations;
}
