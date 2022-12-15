package ohgk.genesis.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {
    
    @Value("${system.project}")
    private String systemProject;

    @Value("${system.comp}")
    private String systemComponent;

    @Bean
    public String getSystemName() {

        return String.format("%s-%s", this.systemProject, this.systemComponent);
    }
}
