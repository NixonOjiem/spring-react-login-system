package com.nickogm.backend.model;

import jakarta.persistence.*; // Use 'javax.persistence.*' if you are on Spring Boot 2 or older

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Will store the HASHED password

    // Constructors (e.g., public User() {}, public User(String username, ...) {})
    // Default constructor required by JPA
    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password; // This should be the encoded password
    }

    // Getters and Setters (REQUIRED for JPA)
    public Long getId() {
        return id;
    }

    public String getUsername() { // <--- THIS is what UserDetailsImpl needs
        return username;
    }

    public String getEmail() { // <--- THIS is what AuthController needs
        return email;
    }

    public String getPassword() { // <--- THIS is what Spring Security needs for authentication
        return password;
    }

    // --- SETTER METHODS (Required for JPA/Hibernate to set values) ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
