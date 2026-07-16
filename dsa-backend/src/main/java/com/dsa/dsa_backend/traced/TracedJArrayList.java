package com.dsa.dsa_backend.traced;

import java.util.ArrayList;
import java.util.List;

import com.dsa.collections.linear.JArrayList;
import com.dsa.dsa_backend.engine.StepRecorder;
import com.dsa.dsa_backend.engine.StepType;

public class TracedJArrayList {
    private JArrayList<Integer> list;
    private StepRecorder recorder;

    private List<Integer> snapshot() {
        List<Integer> updated_list = new ArrayList<>();

        for(int i=0; i<list.size(); i++)
            updated_list.add(list.get(i));

        return updated_list;
    }

    public TracedJArrayList(StepRecorder recorder) {
        this.list = new JArrayList<>();
        this.recorder = recorder;
    }

    /**
     * implementation of methods that will be recorded for visualizing.
     */
    /* CRUD operations */
    boolean add(int element) {
        list.add(element); // actually adding element in JArrayList

        recorder.addRecord(StepType.ADD, snapshot(), new int[] {list.size()-1}, "adding element at last index");

        return true;
    }

    boolean addFirst(int element) {
        list.addFirst(element);

        recorder.addRecord(StepType.SHIFT, snapshot(), new int[] {0}, "shifting all elements to one index right");

        return true;
    }

    boolean remove(int index) {
        list.remove(index);

        recorder.addRecord(StepType.REMOVE, snapshot(), new int[] {index}, "removing element on the index: " + index);

        return true;
    }

    boolean set(int index, int element) {
        list.set(index, element);

        recorder.addRecord(StepType.SET, snapshot(), new int[] {index}, "updating element on the index: " + index);

        return true;
    }

    boolean get(int index) {
        int value = list.get(index);

        recorder.addRecord(StepType.GET, snapshot(), new int[] {index}, "value on the index: " + index + " is: " + value);

        return true;
    }

    /* searching operations */
    boolean linear_search(int target) {
      
        if(!list.contains(target))
        {
            recorder.addRecord(StepType.NOT_FOUND, snapshot(), new int[] {}, "target element: " + target + " not found in the list");
            return false;
        }
    
        for(int i = 0 ; i<list.size() ; i++)
        {
            recorder.addRecord(StepType.COMPARE, snapshot(), new int[] {i}, "comparing element on the index: " + i + " with target: " + target);
            if(list.get(i) == target)
            {
                recorder.addRecord(StepType.FOUND, snapshot(), new int[] {i}, "target element: " + target + " found on the index: " + i);
                return true;
                
            }

        }
        return false;

    }

    boolean binary_search(int target) {



    }

    /* sorting operations */
    boolean bubble_sort() {

        boolean swap = false;
        for(int i = 0 ; i<list.size()-1 ; i++)
        {
            for(int j = 0 ; j<list.size()-i-1 ; j++)
            {
                recorder.addRecord(StepType.COMPARE, snapshot(), new int[] {j, j+1}, "comparing element on the index: " + j + " with element on the index: " + (j+1));
                if(list.get(j) > list.get(j+1))
                {
                    swap = true;
                    int temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                    recorder.addRecord(StepType.SWAP, snapshot(), new int[] {j, j+1}, "swapping element on the index: " + j + " with element on the index: " + (j+1));
                }
            }

            if(!swap)
                break;
            
        }
        return true;


    }

    boolean insertion_sort() {

    }

    boolean merge_sort() {

    }

    boolean quick_sort() {

    }
}