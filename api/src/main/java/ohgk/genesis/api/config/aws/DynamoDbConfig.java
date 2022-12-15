package ohgk.genesis.api.config.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ohgk.genesis.api.entities.Project;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;

@Configuration
public class DynamoDbConfig {
    
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {

        return DynamoDbEnhancedClient.create();
    }

    @Bean
    public BeanTableSchema<Project> getProjectTableSchema() {
        return TableSchema.fromBean(Project.class);
    }
}
