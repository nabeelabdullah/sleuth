package com.monitoring.actuator.controller;

import com.monitoring.actuator.dto.UserDTO;
import com.monitoring.actuator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.POST, value = "/setUser")
    public Boolean setUser(@RequestBody UserDTO userDTO) {
        LOGGER.info("this is request /setUser");
        userService.setUser(userDTO);
        LOGGER.info("got responce /insert");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser")
    public UserDTO setUser(@RequestParam Long Id) {
        return userService.getUser(Id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/insert")
    public Object setUserIndirect(@RequestBody UserDTO userDTO) {
        LOGGER.info("this is request /insert");
        Object object = restTemplate.postForEntity("http://localhost:8081/setUser", userDTO, Object.class);
        LOGGER.info("got responce /insert");
        return object;
    }
}


