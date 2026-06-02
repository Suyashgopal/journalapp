package com.springproj.journalApp.exception;

/** Thrown by the service layer when a requested entity does not exist or is not owned by the caller. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
