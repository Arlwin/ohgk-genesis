package ohgk.genesis.api.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf().disable()
        .cors().and()
        .authorizeRequests()
            // Public
            .antMatchers(HttpMethod.GET, "/api/projects/**")
                .permitAll()
            .antMatchers(
                "/api/users/signUp"
                // "/api/users/login"
            )
                .permitAll()
            // For Admin Only
            .antMatchers(
                "/api/projects/**",
                "/api/users/**"
            )
                .hasRole("ADMIN")
        .and()
        .httpBasic();
        // .formLogin()
        //     .loginPage("/login")
        //     .loginProcessingUrl("/api/users/login")
        //     .defaultSuccessUrl("/ ")
        //     .failureUrl("/error");
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This is for localhost / Access Control Origin error for JSESSIONID cookie to work in Axios
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type",
            "Origin", 
            "X-Auth-Token"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
