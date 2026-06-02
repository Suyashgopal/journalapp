package com.springproj.journalApp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** Validated payload for /public/signup. Decouples the public API from the persistence entity. */
public class SignupRequest {

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 30, message = "username must be 3-30 characters")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 100, message = "password must be at least 6 characters")
    private String password;

    @Email(message = "email must be valid")
    private String email;

    private boolean sentimentAnalysis;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isSentimentAnalysis() { return sentimentAnalysis; }
    public void setSentimentAnalysis(boolean sentimentAnalysis) { this.sentimentAnalysis = sentimentAnalysis; }
}
