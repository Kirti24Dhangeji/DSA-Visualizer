package com.dsa.dsa_backend.engine;

public enum StepType {
    // CRUD
    ADD,
    REMOVE,
    SET,
    GET,

    // Searching & Sorting
    COMPARE,
    SHIFT,
    FOUND, 
    NOT_FOUND,
    SWAP,
    SORT,

    // Failed Step (for handle exception)
    FAILED,

    // Middle element
    RUN,
    MIDDLE,

    // Reverse
    REVERSED
}
