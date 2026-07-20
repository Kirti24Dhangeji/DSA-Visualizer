package com.dsa.dsa_backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class Operation {

    @Getter
    @Setter
    @NotBlank(message = "operation must have a name (operation name)")
    private String name;

    @Getter
    @Setter
    private List<Integer> arguments;

    public Operation() {}

    public Operation(String name, List<Integer> arguments) {
        this.name = name;
        this.arguments = arguments;
    }
}