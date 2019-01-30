package com.monitoring.actuator.service;


import brave.ScopedSpan;
import brave.Tracer;
import com.monitoring.actuator.dto.UserDTO;
import com.monitoring.actuator.mapper.UserMapper;
import com.monitoring.actuator.model.User;
import com.monitoring.actuator.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.SpanName;
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

    @Autowired
    private Tracer tracer;

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
    @SpanName(value = "nabeel2async")
    public void waitTest() {
        Future<String> stringFuture = userMapper.waitTest();
        try {
            Thread.sleep(700);
            System.out.println("Wait over");
            stringFuture.get();
        } catch (Exception er) {
        }

    }


    @Async
    public void waitTest2() {

        try {
            Thread.sleep(200);
            LOGGER.info("Wait over for span newSpan");
        } catch (Exception er) {
            er.printStackTrace();
        }

        ScopedSpan span = tracer.startScopedSpan("newSpan2");
        try {
            Thread.sleep(700);
            LOGGER.info("Wait over for span newSpan");
        } catch (Exception er) {
            span.error(er);
            er.printStackTrace();
        }
        span.finish();


    }
}
