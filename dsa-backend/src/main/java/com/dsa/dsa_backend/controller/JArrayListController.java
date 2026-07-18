package com.dsa.dsa_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsa.dsa_backend.engine.ExecutionTrace;
import com.dsa.dsa_backend.engine.StepRecorder;
import com.dsa.dsa_backend.engine.Step;
import com.dsa.dsa_backend.traced.TracedJArrayList;

@RestController
@RequestMapping("/api/arraylist")
public class JArrayListController {
    private final StepRecorder recorder;
    private final TracedJArrayList arrayList;

    public JArrayListController() {
        recorder = new StepRecorder();
        arrayList = new TracedJArrayList(recorder);
    }

    @PostMapping
    public List<Step> execute(ExecutionRequest request) {
        // call functions as per user request (operation order)

        ExecutionTrace holder = recorder.buildTrace();
        return holder.getSteps();
    }
}