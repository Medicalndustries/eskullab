package github.medicalindustries.eskullab.service;

import github.medicalindustries.eskullab.data.Role;
import github.medicalindustries.eskullab.repository.UserRepository;
import github.medicalindustries.eskullab.data.User;
import github.medicalindustries.eskullab.data.UserDTO;
import github.medicalindustries.eskullab.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository USER_REPOSITORY;
    private final RoleRepository ROLE_REPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.USER_REPOSITORY = userRepository;
        this.ROLE_REPOSITORY = roleRepository;
        this.PASSWORD_ENCODER = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(PASSWORD_ENCODER.encode(userDto.getPassword()));

        Role role = ROLE_REPOSITORY.findByName("USER");
        if (role == null) {
            role = checkIfRoleExistsOrCreate();
        }

        user.setRoles(List.of(role));
        USER_REPOSITORY.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return USER_REPOSITORY.findByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = USER_REPOSITORY.findAll();

        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }

    private UserDTO mapToUserDto(User user) {
        UserDTO userDto = new UserDTO();
        String[] str = user.getUsername().split(" ");
        userDto.setUsername(str[0]);
        return userDto;
    }

    private Role checkIfRoleExistsOrCreate() {
        Role role = new Role();
        role.setName("USER");
        return ROLE_REPOSITORY.save(role);
    }
}
