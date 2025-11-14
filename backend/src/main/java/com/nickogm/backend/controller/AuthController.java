package com.nickogm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.nickogm.backend.model.User;
import com.nickogm.backend.repository.UserRepository;
import com.nickogm.backend.payload.request.SignupRequest;
import com.nickogm.backend.payload.request.LoginRequest;
import com.nickogm.backend.payload.response.JwtResponse; // We'll fill this in later

@CrossOrigin(origins = "*", maxAge = 3600) // Allows cross-origin requests from React
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder; // You must define this bean in your SecurityConfig

    // AuthenticationManager and JwtUtils will be Autowired later once Spring
    // Security is set up

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // --- JWT LOGIN LOGIC GOES HERE ---
        // 1. Authenticate user credentials using AuthenticationManager
        // 2. Generate a JWT token using JwtUtils
        // 3. Return JwtResponse

        // TEMPORARY RETURN until Spring Security is configured:
        return ResponseEntity.ok("Login endpoint reached successfully. Configure Spring Security next!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        // 1. Check if username or email is already taken
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // 2. Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                // HASH the password before saving!
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}