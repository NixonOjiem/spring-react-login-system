package com.nickogm.backend.payload.request;

public class SignupRequest {
    private String username;
    private String email;
    private String password;

    // --- GETTER METHODS ---
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
