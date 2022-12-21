package ohgk.genesis.api.services;

import java.util.List;

import ohgk.genesis.api.exceptions.InvalidUserException;
import ohgk.genesis.api.models.dto.UserDto;

public interface UserService {
        
    // Create
    public UserDto createUser(UserDto user, boolean signUp) throws InvalidUserException; 

    // Read
    public List<UserDto> getUsers();
    public UserDto getUserByUsername(String username) throws InvalidUserException;

    // Update
    public UserDto updateUser(UserDto user) throws InvalidUserException;

    // Delete
    public UserDto deleteUserByUsername(String username) throws InvalidUserException;
}
