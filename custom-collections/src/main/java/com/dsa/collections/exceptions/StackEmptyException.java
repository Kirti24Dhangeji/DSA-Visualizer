package com.dsa.collections.exceptions;

public class StackEmptyException extends RuntimeException {
    private static final long serialVersionUID = 2L;
    public StackEmptyException(String msg) {
        super(msg);
    }
}
