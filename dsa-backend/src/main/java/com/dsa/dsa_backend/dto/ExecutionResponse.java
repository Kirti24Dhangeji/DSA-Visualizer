package com.dsa.dsa_backend.dto;

import java.util.List;

import com.dsa.dsa_backend.engine.ExecutionTrace;
import com.dsa.dsa_backend.engine.Step;

import lombok.Getter;

public class ExecutionResponse
{
    @Getter
    private final List<Step> steps;
    private final int totalSteps;

    public ExecutionResponse(ExecutionTrace trace)
    {
        this.steps = trace.getSteps();
        this.totalSteps = trace.totalSteps();
    }

}