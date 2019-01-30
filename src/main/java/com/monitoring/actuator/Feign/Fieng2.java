package com.monitoring.actuator.Feign;

import com.monitoring.actuator.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8081", value = "ActuatorClient2")
public interface Fieng2 {

    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    UserDTO findById(@RequestParam Long Id);

}
