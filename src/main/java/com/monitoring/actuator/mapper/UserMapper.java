package com.monitoring.actuator.mapper;

import com.monitoring.actuator.dto.UserDTO;
import com.monitoring.actuator.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@EnableAsync
public class UserMapper {


    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserDTO mapToUserDTO(User user) {
        LOGGER.info("map to user caLL START");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setVal(user.getVal());
        userDTO.setName(user.getName());
        LOGGER.info("map to user caLL END");
        return userDTO;
    }

    public User mapToUser(UserDTO userDTO) {
        LOGGER.info("map to user caLL START");
        User user = new User();
        user.setId(userDTO.getId());
        user.setVal(userDTO.getVal());
        user.setName(userDTO.getName());
        LOGGER.info("map to user caLL END");
        return user;
    }

    @Async
    public Future<String> waitTest() {
        try {
            Thread.sleep(400);
        } catch (Exception er) {
        }
        return new AsyncResult<>("");
    }


}
