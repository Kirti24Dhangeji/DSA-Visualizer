package com.dsa.dsa_backend.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class ExecutionRequest {

    @NotEmpty(message = "At least one operation is required")
    @Valid
    private List<Operation> operations;

    public ExecutionRequest() {}

    public ExecutionRequest(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}