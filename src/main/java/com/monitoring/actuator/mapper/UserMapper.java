package com.monitoring.actuator.mapper;

import com.monitoring.actuator.dto.UserDTO;
import com.monitoring.actuator.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setVal(user.getVal());
        userDTO.setName(user.getName());
        return userDTO;
    }

    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setVal(userDTO.getVal());
        user.setName(userDTO.getName());
        return user;
    }
}
