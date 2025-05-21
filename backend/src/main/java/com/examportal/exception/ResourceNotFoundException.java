// filepath: c:\Users\2407106\project\online-exam-portal\backend\src\main\java\com\examportal\exception\ResourceNotFoundException.java
package com.examportal.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}