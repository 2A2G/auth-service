package com.authservice.auth_service.service;

import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.repository.UserRepository;
import com.authservice.auth_service.request.UpdatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void updatePassword(String email, UpdatePassword updatePassword) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
                    userRepository.save(user);
                } else {
                    throw new IllegalArgumentException("Current password is incorrect");
                }
            } else {
                throw new IllegalArgumentException("User not found with email: " + email);
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw e;
            }
            throw new RuntimeException("Error updating password: " + e.getMessage());
        }
    }
}
