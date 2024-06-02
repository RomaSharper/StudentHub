package com.java.app.exceptions;

public class PerformanceNotFoundException extends RuntimeException {
    public PerformanceNotFoundException(String message) {
        super(message);
    }
}
