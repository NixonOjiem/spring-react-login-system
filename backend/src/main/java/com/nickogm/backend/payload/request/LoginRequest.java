package com.nickogm.backend.payload.request;

public class LoginRequest {
    private String username;
    private String password;

    // Default Constructor (required by Jackson for JSON deserialization)
    public LoginRequest() {
    }

    // --- GETTERS ---
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // --- SETTERS ---
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
