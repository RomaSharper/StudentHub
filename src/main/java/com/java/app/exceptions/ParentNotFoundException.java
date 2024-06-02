package com.java.app.exceptions;

public class ParentNotFoundException extends RuntimeException {
    public ParentNotFoundException(String message) {
        super(message);
    }
}
