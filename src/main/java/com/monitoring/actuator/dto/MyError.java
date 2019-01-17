package com.monitoring.actuator.dto;

import java.io.Serializable;

public class MyError implements Serializable {

    private String type;

    private String value;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
