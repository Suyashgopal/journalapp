package com.springproj.journalApp.dto;

import java.time.LocalDateTime;

/**
 * Uniform error body returned by every endpoint via {@link com.springproj.journalApp.exception.GlobalExceptionHandler}.
 * Replaces the previous mix of bare strings, echoed request bodies and leaked stack traces.
 */
public class ApiError {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;

    public ApiError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
}
