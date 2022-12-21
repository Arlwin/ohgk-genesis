package ohgk.genesis.api.services.ddb;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ohgk.genesis.api.config.SystemConfig;
import ohgk.genesis.api.entities.User;
import ohgk.genesis.api.enums.UserRoleEnum;
import ohgk.genesis.api.exceptions.InvalidUserException;
import ohgk.genesis.api.models.dto.UserDto;
import ohgk.genesis.api.services.UserService;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;

@Slf4j
@Service
public class DdbUserServiceImpl implements UserService {

    private static final Set<UserRoleEnum> DEFAULT_ROLES = new HashSet<>(Arrays.asList(UserRoleEnum.USER));

    private PasswordEncoder passwordEncoder;
    private Validator validator;
    private DynamoDbTable<User> ddbTable;

    @Autowired
    public DdbUserServiceImpl(
        PasswordEncoder passwordEncoder,
        SystemConfig config,
        DynamoDbEnhancedClient dynamoDbEnhancedClient,
        BeanTableSchema<User> userTableSchema,
        Validator validator
    ) {

        this.passwordEncoder = passwordEncoder;
        this.validator = validator;

        String tableName = String.format("%s-%s-table",
            config.getSystemName(),
            User.TABLE_NAME
        );

        this.ddbTable = dynamoDbEnhancedClient.table(
            tableName, 
            userTableSchema
        );
    }

    @Override
    public UserDto createUser(UserDto user, boolean signUp) throws InvalidUserException {
        
        var violations = validator.validate(user);

        if (violations.size() != 0) {

            throw InvalidUserException.invalidUserDto(violations);
        }

        // Check if User exists
        var existingUser = this.getUserByUsername(user.getUsername());

        if (existingUser != null) throw InvalidUserException.userAlreadyExists(user.getUsername());

        // Hash the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles().isEmpty() || signUp ? DEFAULT_ROLES : user.getRoles()); // Default Role

        this.ddbTable.putItem(user.toEntity());

        // Clean up output
        if (signUp) user.setPassword(null);

        return user;
    }

    @Override
    public List<UserDto> getUsers() {

        List<UserDto> users = this.ddbTable.scan().items().stream()
            .map(
                (item) -> {
                    return UserDto.fromEntity(item);
                }
            ).collect(Collectors.toList());

        log.info(users.size() + "");

        return users;
    }

    @Override
    public UserDto getUserByUsername(String username) throws InvalidUserException {
        
        Key key = Key.builder()
            .partitionValue(username)
            .build();

        var user = this.ddbTable.getItem(key);

        if (user == null) throw InvalidUserException.userDoesNotExist(username);

        return UserDto.fromEntity(user);
    }

    @Override
    public UserDto updateUser(UserDto user) throws InvalidUserException {

        var violations = validator.validate(user);

        if (violations.size() != 0) {

            throw InvalidUserException.invalidUserDto(violations);
        }

        // User must exist
        if (getUserByUsername(user.getUsername()) == null) throw InvalidUserException.userDoesNotExist(user.getUsername());

        // Hash the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());

        this.ddbTable.putItem(user.toEntity());

        // Clean up output
        user.setPassword(null);

        return user;
    }

    @Override
    public UserDto deleteUserByUsername(String username) throws InvalidUserException {

        Key key = Key.builder()
            .partitionValue(username)
            .build();

        var result = this.ddbTable.deleteItem(key);

        if (result == null) throw InvalidUserException.userDoesNotExist(username);

        return UserDto.fromEntity(result);

    }
}
