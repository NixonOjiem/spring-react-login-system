package com.nickogm.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nickogm.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method to find a user by username
    Optional<User> findByUsername(String username);

    // Custom method to check if a username exists
    Boolean existsByUsername(String username);

    // Custom method to check if an email exists
    Boolean existsByEmail(String email);
}
