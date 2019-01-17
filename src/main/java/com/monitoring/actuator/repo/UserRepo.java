package com.monitoring.actuator.repo;


import com.monitoring.actuator.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
