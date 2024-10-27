package github.medicalindustries.eskullab.service;

import github.medicalindustries.eskullab.data.User;
import github.medicalindustries.eskullab.data.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);
    User findUserByUsername(String username);

    List<UserDTO> findAllUsers();
}
