package com.monitoring.actuator;

import com.monitoring.actuator.model.User;
import com.monitoring.actuator.repo.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActuatorApplicationTests {


    @Autowired
    private UserRepo userRepo;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setVal("1");
        user.setName("nabeel");
        userRepo.save(user);
        System.out.println("hello");
    }

}

