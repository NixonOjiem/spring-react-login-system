package com.nickogm.backend.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    public JwtResponse(String accessToken, Long id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // --- GETTERS ---
    public String getAccessToken() {
        return token;
    }

    public String getTokenType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    // --- SETTERS (Optional, but good practice if token might be updated) ---
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }
}
