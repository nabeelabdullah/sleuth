package com.monitoring.actuator.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String val;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
