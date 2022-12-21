package ohgk.genesis.api.entities;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohgk.genesis.api.enums.UserRoleEnum;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean // AWS DDB
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    public static final String TABLE_NAME = "users";
    
    private String username;
    private String email;
    private String password;

    private String firstName;
    private String lastName;

    private Set<UserRoleEnum> roles; 

    @DynamoDbPartitionKey // AWS DDB
    public String getUsername(){
        return this.username;
    }
}
