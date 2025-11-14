package com.nickogm.backend.security.services;

import com.nickogm.backend.model.User;
import com.nickogm.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nickogm.backend.security.services.UserDetailsImpl;

@Service // Register as a Spring Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch User from the database using the UserRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // 2. Convert the User entity into the Spring Security UserDetails object
        // FIX: Change 'UserDetailsServiceImpl' to 'UserDetailsImpl'
        return UserDetailsImpl.build(user);
    }
}