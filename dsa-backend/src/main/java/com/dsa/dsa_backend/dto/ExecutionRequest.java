package com.dsa.dsa_backend.dto;

import java.util.List;

import lombok.Getter;

public class ExecutionRequest {

    @Getter
    private List<Operation> operations;

    public ExecutionRequest() {}

    public ExecutionRequest(List<Operation> operations) {
        this.operations = operations;
    }
}