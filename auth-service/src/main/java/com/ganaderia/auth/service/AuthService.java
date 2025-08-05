package com.ganaderia.auth.service;

import com.ganaderia.auth.dto.request.LoginRequest;
import com.ganaderia.auth.dto.request.RegisterRequest;
import com.ganaderia.auth.dto.response.LoginResponse;
import com.ganaderia.auth.dto.response.UserResponse;
import com.ganaderia.auth.entity.User;
import com.ganaderia.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {

        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            // Generate JWT token
            String token = jwtService.generateToken(user);
            Long expiresIn = jwtService.getExpirationTime();

            // Create user response
            UserResponse userResponse = createUserResponse(user);

            return new LoginResponse(token, expiresIn, userResponse);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Username or password no es válido", e);
        }
    }

    public UserResponse register(RegisterRequest request) {
        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setRole(User.Role.valueOf(request.getRole().toUpperCase()));
        user.setEstablishmentId(request.getEstablishmentId());
        user.setActive(true);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        // ⭐ AGREGAR ESTE LOGGING
        System.out.println("=== REGISTRO DEBUG ===");
        System.out.println("Password original: " + request.getPassword());
        System.out.println("Password hasheado: " + hashedPassword);
        System.out.println("Encoder class: " + passwordEncoder.getClass().getName());

        User savedUser = userRepository.save(user);
        return createUserResponse(savedUser);
    }

    public UserResponse validateToken(String token) {
        if (!jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getActive()) {
            throw new RuntimeException("User account is deactivated");
        }

        return createUserResponse(user);
    }

    private UserResponse createUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().name());
        response.setEstablishmentId(user.getEstablishmentId());
        response.setActive(user.getActive());
        response.setLastLogin(user.getLastLogin());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
