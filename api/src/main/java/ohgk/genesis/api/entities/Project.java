package ohgk.genesis.api.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohgk.genesis.api.enums.ProjectStatusEnum;
import ohgk.genesis.api.enums.ProjectTypeEnum;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean // AWS DDB
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    public static final String TABLE_NAME = "projects";
    
    private String id;

    private String name;
    private String description;
    private List<String> languages;
    private ProjectTypeEnum type;

    private ProjectStatusEnum status;
    private String url;

    @DynamoDbPartitionKey // AWS DDB
    public String getId(){
        return this.id;
    }
}
 