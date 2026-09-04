package com.dsa.dsa_backend.engine;

public enum StepType {
    // CRUD
    ADD,
    REMOVE,
    SET,
    GET,

    // searching & sorting
    COMPARE,
    SHIFT,
    FOUND, 
    NOT_FOUND,
    SWAP,
    SORT,

    // failed step (for handle exception)
    FAILED,

    //Middle element
    RUN,
    MIDDLE
}
