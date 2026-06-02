package com.springproj.journalApp.dto;

import javax.validation.constraints.Size;

/**
 * Payload for PUT /user. All fields are optional (partial update); only non-blank
 * values are applied. Using a DTO instead of the entity avoids the entity's
 * Lombok @NonNull constraints firing during JSON deserialization.
 */
public class UpdateUserRequest {

    @Size(min = 3, max = 30, message = "username must be 3-30 characters")
    private String username;

    @Size(min = 6, max = 100, message = "password must be at least 6 characters")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
