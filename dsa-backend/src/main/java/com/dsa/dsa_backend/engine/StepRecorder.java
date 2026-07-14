package com.dsa.dsa_backend.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * It will store steps of execution for one operation
 * after each operation (user request) steps list will be exported
 * and after that it will be clear for another operarations.
 */
public class StepRecorder {
    private final List<Step> steps;

    public StepRecorder() {
        steps = new ArrayList<>();
    }

    public boolean addRecord(StepType stepType, List<Integer> snapshot, int highlightedIndices[], String description) {
        return steps.add(new Step(stepType, snapshot, highlightedIndices, description));
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void clearRecord() {
        steps.clear();
    }
}