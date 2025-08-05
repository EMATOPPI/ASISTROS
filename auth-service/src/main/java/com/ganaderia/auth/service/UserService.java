package com.ganaderia.auth.service;

import com.ganaderia.auth.entity.User;
import com.ganaderia.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("=== LOADING USER ===");
        System.out.println("Looking for: " + usernameOrEmail);

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> {
                    System.out.println("USER NOT FOUND: " + usernameOrEmail);
                    return new UsernameNotFoundException("User not found: " + usernameOrEmail);
                });

        System.out.println("USER FOUND: " + user.getUsername() + ", Active: " + user.getActive());

        // ⭐ AGREGAR ESTE LOGGING
        System.out.println("Password stored in DB: " + user.getPassword());
        System.out.println("Password length: " + user.getPassword().length());
        System.out.println("Password starts with $2a$: " + user.getPassword().startsWith("$2a$"));

        if (!user.getActive()) {
            throw new UsernameNotFoundException("User account is deactivated");
        }

        return user;
    }
}
