package ohgk.genesis.api.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    
    private ValidatorFactory validatorFactory;

    @Bean
    public Validator validator() {

        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        return this.validatorFactory.getValidator();
    }
}
