package ohgk.genesis.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/**") // ! TEMP
            // .permitAll()
            .permitAll()
        .anyRequest()
            .hasRole("USER");
        
        return http.build();
    }
}
