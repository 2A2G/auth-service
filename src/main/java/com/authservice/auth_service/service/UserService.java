package com.authservice.auth_service.service;

import com.authservice.auth_service.config.Jwt.JwtConfig;
import com.authservice.auth_service.config.JwtBlacklistService;
import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.request.LoginRequest;
import com.authservice.auth_service.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtConfig jwtConfig;
    private final JwtBlacklistService jwtBlacklistService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtConfig jwtConfig, JwtBlacklistService jwtBlacklistService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtConfig = jwtConfig;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    public String accessLogin(LoginRequest user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        System.out.println("Searching for user with email: " + user.getEmail());

        User foundUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        System.out.println("User found: " + foundUser.getEmail());

        if (!encoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        if (foundUser.getRole() == null) {
            throw new IllegalArgumentException("The user has no assigned role.");
        }

        return jwtConfig.generateToken(foundUser);
    }


    public String newUser(final User user) {
        try {
            userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
                throw new IllegalArgumentException("The email is already registered.");
            });

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User created successfully.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            throw new IllegalArgumentException("Error registering user. Please try again later.");
        }
    }


    public ResponseEntity<String> logout(String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token cannot be empty.");
        }
        ResponseEntity<String> blacklistResponse = jwtBlacklistService.addToBlacklist(token);

        if (blacklistResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Logout successful: token added to the blacklist.");
        } else {
            return blacklistResponse;
        }
    }


}
