package ohgk.genesis.api.models.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import ohgk.genesis.api.entities.User;
import ohgk.genesis.api.enums.UserRoleEnum;
import ohgk.genesis.api.models.security.UserDetailsImpl;

@JsonInclude(value = Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String firstName;
    private String lastName;

    @Default
    private Set<UserRoleEnum> roles = new HashSet<>();

    public static UserDto fromEntity(User entity){

        return new UserDto(
            entity.getUsername(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getRoles()
        );
    }

    public User toEntity() {

        return User.builder()
            .username(this.username)
            .email(this.email)
            .password(this.password)
            .firstName(this.firstName)
            .lastName(this.lastName)
            .roles(this.roles)
            .build();
    }

    public UserDetails toUserDetails() {

        return UserDetailsImpl.builder()
            .username(this.username)
            .password(this.password)
            .roles(this.roles)
            .build();
    }
}
