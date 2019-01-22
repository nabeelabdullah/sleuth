package com.monitoring.actuator.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class MyError implements Serializable {

    private String type;

    private String value;
}
