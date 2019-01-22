package com.monitoring.actuator.service;


import com.monitoring.actuator.dto.UserDTO;
import com.monitoring.actuator.mapper.UserMapper;
import com.monitoring.actuator.model.User;
import com.monitoring.actuator.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Future;


@Service
@EnableAsync
public class UserService {


    private final UserRepo userRepo;


    private final UserMapper userMapper;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }


    public void setUser(UserDTO userDTO) {
        try {
            LOGGER.info(String.format("New Request for Name %s , Val %s", userDTO.getName(), userDTO.getVal()));
            User user = userMapper.mapToUser(userDTO);
            userRepo.save(user);
            LOGGER.info(String.format("Request Finished %s , Val %s", userDTO.getName(), userDTO.getVal()));
        } catch (Exception er) {
            LOGGER.info(String.format("Request failed %s , Val %s", userDTO.getName(), userDTO.getVal()), er);
            throw er;
        }
    }

    public UserDTO getUser(Long Id) {
        UserDTO userDTO = null;
        try {
            LOGGER.info(String.format("New Request for Id %s", Id));
            Optional<User> opt = userRepo.findById(Id);
            if (opt.isPresent()) {
                userDTO = userMapper.mapToUserDTO(opt.get());
            }
            LOGGER.info(String.format("Request Finished for Id %s", Id));
        } catch (Exception er) {
            LOGGER.info(String.format("Request Failed for Id %s", Id), er);
            throw er;
        }
        return userDTO;
    }

    @Async
    public void waitTest() {
        Future<String> stringFuture = userMapper.waitTest();
        try {
            Thread.sleep(700);
            stringFuture.get();
        } catch (Exception er) {
        }

    }
}
