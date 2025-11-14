package com.nickogm.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Tells Spring this class contains configuration
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    // This method defines and returns the PasswordEncoder bean.
    // Spring will now be able to inject this into your AuthController.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt is the standard, secure implementation
        return new BCryptPasswordEncoder();
    }

    // 2. CONFIGURE THE SECURITY FILTER CHAIN (Fixes 401 error)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless REST APIs using JWT
                .csrf(csrf -> csrf.disable())

                // Set session management to stateless (no session cookies)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        // ALLOW public access to the sign-up and sign-in paths
                        .requestMatchers("/api/auth/**").permitAll()

                        // REQUIRE authentication for all other requests
                        .anyRequest().authenticated());

        return http.build();
    }
}
