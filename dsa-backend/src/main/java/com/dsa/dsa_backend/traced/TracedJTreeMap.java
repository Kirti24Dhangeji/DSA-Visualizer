package com.dsa.dsa_backend.traced;

import com.dsa.collections.associative.JTreeMap;
import com.dsa.dsa_backend.engine.StepRecorder;
import com.dsa.dsa_backend.engine.StepType;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TracedJTreeMap {
    private JTreeMap<Integer, String> map;
    private StepRecorder recorder;

    public TracedJTreeMap(StepRecorder recorder) {
        this.map = new JTreeMap<>();
        this.recorder = recorder;
    }

    private List<Object> snapshot() {
        List<? extends Object> preorder;
        preorder = map.getPreorder();
        return Collections.singletonList(preorder);
    }

    public boolean insert(int element) {
        
    }

    public boolean delete(int element) {
        
    }

    public boolean search(int element) {
        
    }
}
