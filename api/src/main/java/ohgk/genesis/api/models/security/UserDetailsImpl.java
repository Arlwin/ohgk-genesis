package ohgk.genesis.api.models.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohgk.genesis.api.enums.UserRoleEnum;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDetailsImpl implements UserDetails {
    
    private String username;
    private String password;

    private Set<UserRoleEnum> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return this.roles.stream()
            .map(
                (role) -> {
                    // return new SimpleGrantedAuthority(String.format("ROLE_%s", role.));
                    return new SimpleGrantedAuthority("ROLE_" + role.name().toUpperCase());
                }
            ).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    
}
