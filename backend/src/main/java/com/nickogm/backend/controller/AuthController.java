package com.nickogm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.nickogm.backend.model.User;
import com.nickogm.backend.repository.UserRepository;
import com.nickogm.backend.security.services.UserDetailsImpl;
import com.nickogm.backend.payload.request.SignupRequest;
import com.nickogm.backend.payload.request.LoginRequest;
import com.nickogm.backend.payload.response.JwtResponse; // We'll fill this in later
import com.nickogm.backend.security.jwt.JwtUtils;
import org.springframework.security.core.Authentication;

@CrossOrigin(origins = "*", maxAge = 3600) // Allows cross-origin requests from React
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder; // You must define this bean in your SecurityConfig

    // AuthenticationManager and JwtUtils will be Autowired later once Spring
    // Security is set up

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // 1. Authenticate user credentials using AuthenticationManager

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        // Set the authenticated object in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Generate a JWT token using JwtUtils
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Extract the user details from the authenticated object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // 3. Return JwtResponse
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        // If you had roles, they would be extracted here too
        ));
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