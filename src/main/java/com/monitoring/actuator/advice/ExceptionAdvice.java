package com.monitoring.actuator.advice;

import com.monitoring.actuator.dto.MyError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(value = "com.monitoring.actuator.controller")
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyError> notFoundException(final Exception e) {
        MyError error = new MyError();
        error.setType(e.getClass().getName());
        error.setValue(e.getMessage());
        error.getType();
        ResponseEntity<MyError> responseEntity = new ResponseEntity<MyError>(error, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
