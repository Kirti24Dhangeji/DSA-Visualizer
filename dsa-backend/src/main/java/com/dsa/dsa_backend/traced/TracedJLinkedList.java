package com.dsa.dsa_backend.traced;

import java.util.*;

import com.dsa.collections.linear.JArrayList;
import com.dsa.collections.linear.JLinkedList;
import com.dsa.dsa_backend.engine.StepRecorder;
import com.dsa.dsa_backend.engine.StepType;


public class TracedJLinkedList {

    private final JLinkedList<Integer> list;
    private final StepRecorder recorder;

    private List<Integer> snapshot() {
        List<Integer> updated_list = new ArrayList<>();

        for(int i=0; i<list.size(); i++)
            updated_list.add(list.get(i));

        return updated_list;
    }

    public TracedJLinkedList(StepRecorder recorder) {

        this.list = new JLinkedList<>();
        this.recorder = recorder;

    }

    //CRUD operations 

    public boolean add(int element) {

        list.add(element); // actually adding element in JArrayList

        recorder.addRecord(
            StepType.ADD,
            snapshot(),
            new int[] {list.size()-1},
            Map.of(),
            "adding element at last index"
        );

        return true;
    }

        public boolean addFirst(int element) {
        list.addFirst(element);

        recorder.addRecord(
            StepType.SHIFT,
            snapshot(),
            new int[] {0},
            Map.of(),
            "shifting all elements to one index right"
        );
        return true;
    }

    public boolean remove(int index) {
        try {
            list.remove(index);

            recorder.addRecord(
                StepType.REMOVE,
                snapshot(),
                new int[] {index},
                Map.of(),
                "removing element on the index: " + index
            );
        } catch(IllegalArgumentException e) {
            recorder.addRecord(
                StepType.FAILED,
                snapshot(),
                new int[] {},
                Map.of(),
                "ERROR: Cannot remove element from an empty array.!"
            );
        }
        return true;
    }

    public boolean set(int index, int element) {
        try {
            list.set(index, element);

            recorder.addRecord(
                StepType.SET,
                snapshot(),
                new int[] {index},
                Map.of(),
                "updating element on the index: " + index
            );
        } catch(IllegalArgumentException e) {
            recorder.addRecord(
                StepType.FAILED,
                snapshot(),
                new int[] {},
                Map.of(),
                "ERROR: Cannot set (update) element in an empty array.!"
            );
        }
        return true;
    }

    public boolean get(int index) {
        try {
            int value = list.get(index);

            recorder.addRecord(
                StepType.GET,
                snapshot(),
                new int[] {index},
                Map.of(),
                "value on the index: " + index + " is: " + value
            );
        } catch(IllegalArgumentException e) {
            recorder.addRecord(
                StepType.FAILED,
                snapshot(),
                new int[] {},
                Map.of(),
                "ERROR: Cannot fetch element from an empty array.!"
            );
        }
        return true;
    }

    //Linear seearch only..as binary search is not efficient on linked list

    public boolean linear_search(int target) {
        for(int i = 0 ; i<list.size() ; i++)
        {
            recorder.addRecord(
                StepType.COMPARE,
                snapshot(),
                new int[] {i},
                Map.of(),
                "comparing element on the index: " + i + " with target: " + target
            );
            if(list.get(i) == target)
            {
                recorder.addRecord(
                    StepType.FOUND,
                    snapshot(),
                    new int[] {i},
                    Map.of(),
                    "target element: " + target + " found on the index: " + i
                );
                return true;
            }
        }

        recorder.addRecord(
            StepType.NOT_FOUND,
            snapshot(),
            new int[] {},
            Map.of(),
            "target element: " + target + " not found in the list"
        );
        
        return true;
    }

    //SORTING OPERATIONS
    //need to figure which sorting algorithm is best suited for linked list AND implement it here
    
}
