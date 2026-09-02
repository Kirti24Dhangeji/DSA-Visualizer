package com.dsa.dsa_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsa.dsa_backend.dto.ExecutionRequest;
import com.dsa.dsa_backend.dto.ExecutionResponse;
import com.dsa.dsa_backend.dto.Operation;
import com.dsa.dsa_backend.engine.ExecutionTrace;
import com.dsa.dsa_backend.engine.StepRecorder;
import com.dsa.dsa_backend.traced.TracedJLinkedList;


@RestController
@RequestMapping("/api/linkedlist")
public class JLinkedListController {

    @PostMapping("/execute") // POST localhost:8080/api/linkedlist/execute
    public ExecutionResponse execute(@RequestBody ExecutionRequest request) {
        StepRecorder recorder = new StepRecorder();
        TracedJLinkedList linkedList = new TracedJLinkedList(recorder);

        for(Operation op : request.getOperations()) {
            try{
                switch (op.getName()) {
                    case "add" -> {
                        
                        if(!linkedList.add(op.getArguments().get(0)))
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }
                    }

                    case "addFirst" -> {

                        if(!linkedList.addFirst(op.getArguments().get(0)))
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    case "remove" -> {

                        if(!linkedList.remove(op.getArguments().get(0)))
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    case "set" -> {

                        if(!linkedList.set(op.getArguments().get(0), op.getArguments().get(1)))
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    case "get" -> {

                        if(!linkedList.get(op.getArguments().get(0)))
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    case "linearSearch" -> {

                        if(!linkedList.linear_search(op.getArguments().get(0)))
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    case "reverse" -> {

                        if(!linkedList.reverse())
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }


                    case "insertionSort" -> {

                        if(!linkedList.insertion_sort())
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    case "middleNode" -> {

                        if(!linkedList.middleNode())
                        {
                            throw new IllegalCallerException("steps not recorded.!");
                        }

                    }

                    default -> throw new IllegalArgumentException("invalid operation.!");

                }
            } catch(IllegalCallerException e )
            {
                System.out.println("ERROR: " + e);
            }
        }
        

        ExecutionTrace holder = recorder.buildTrace();
        return new ExecutionResponse(holder);
    }
}