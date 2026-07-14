package com.dsa.dsa_backend.engine;

import java.util.List;

/**
 * It is one state of execution steps:
 * The content in step is not modifiable
 * For next execution step, new step object will be created, that's why all fields are final
 */
public class Step {
    private final StepType stepType;
    private final List<Integer> snapshot;
    private final int highlightedIndices[];
    private final String description;

    // Constructor
    public Step(
        StepType stepType,
        List<Integer> snapshot,
        int highlightedIndices[],
        String description
    ) {
        this.stepType = stepType;
        this.snapshot = snapshot;
        this.highlightedIndices = highlightedIndices;
        this.description = description;
    }

    // Getters
    public StepType getStepType() {return stepType;}
    public List<Integer> getSnapshot() {return snapshot;}
    public int[] getHighlightedIndeices() {return highlightedIndices;}
    public String getDescription() {return description;}
}