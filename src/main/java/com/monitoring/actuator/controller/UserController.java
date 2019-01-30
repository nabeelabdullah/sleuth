package com.monitoring.actuator.controller;

import brave.Tracer;
import brave.propagation.TraceContext;
import com.monitoring.actuator.Feign.ActuatorClient;
import com.monitoring.actuator.Feign.Fieng2;
import com.monitoring.actuator.dto.UserDTO;
import com.monitoring.actuator.kafka.KafkaProducer;
import com.monitoring.actuator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ActuatorClient actuatorClient;

    @Autowired
    private Fieng2 fieng2;

    @Autowired
    private KafkaProducer kafkaProducer;

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
        //  userService.waitTest();
        //userService.waitTest2();
        return userService.getUser(Id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/insert")
    public Object setUserIndirect(@RequestBody UserDTO userDTO) {
        LOGGER.info("this is request /insert");
        Object object = restTemplate.postForEntity("http://localhost:8081/setUser", userDTO, Object.class);
        LOGGER.info("got responce /insert");
        return object;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get2")
    @SpanName(value = "nabeel")
    public Object getUserIndirect(@RequestParam Long Id) {
        LOGGER.info("this is request /insert");
        UserDTO userDTO = actuatorClient.findById(Id);
        return userDTO;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public Object getUserIndirect2(@RequestParam Long Id) {
        LOGGER.info("this is request /insert");
        UserDTO userDTO = fieng2.findById(Id);
        return userDTO;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/produce")
    public void produce(@RequestParam String value) {
        LOGGER.info("procing " + value);
        kafkaProducer.send("key ", value);
    }

    @Override
    public String toString() {
        return "ToString";
    }
}


