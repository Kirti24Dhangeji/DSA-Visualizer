package com.dsa.collections.exceptions;

public class ListEmptyException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ListEmptyException(String msg) {
        super(msg);
    }
}