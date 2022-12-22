package ohgk.genesis.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf().disable()
        .authorizeRequests()
            // Public
            .antMatchers(HttpMethod.GET, "/api/projects/**")
                .permitAll()
            .antMatchers(
                "/api/users/signUp",
                "api/users/login"
            )
                .permitAll()
            // For Admin Only
            .antMatchers(
                "/api/projects/**",
                "/api/users/**"
            )
                .hasRole("ADMIN")
        .and()
        .formLogin()
            .loginProcessingUrl("/api/users/login");
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
