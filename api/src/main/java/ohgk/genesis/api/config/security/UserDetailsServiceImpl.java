package ohgk.genesis.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.exceptions.InvalidUserException;
import ohgk.genesis.api.models.dto.UserDto;
import ohgk.genesis.api.services.UserService;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {

        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user;

        try {

            user = this.userService.getUserByUsername(username);
        } catch (InvalidUserException ex) {

            throw new UsernameNotFoundException(ex.getMessage());
        }
        
        return user.toUserDetails();
    }
}
