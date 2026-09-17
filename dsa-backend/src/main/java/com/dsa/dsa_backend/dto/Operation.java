package com.dsa.dsa_backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class Operation {

    @NotBlank(message = "operation must have a name (operation name)")
    private String name;

    private List<Object> arguments;

    public Operation() {}

    public Operation(String name, List<Object> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }
}