package com.authservice.auth_service.controller;

import com.authservice.auth_service.config.Jwt.JwtConfig;
import com.authservice.auth_service.request.UpdatePassword;
import com.authservice.auth_service.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-serve/password")
public class PasswordController {
    private final PasswordService passwordService;
    private final JwtConfig jwtConfig;

    public PasswordController(PasswordService passwordService, JwtConfig jwtConfig) {
        this.passwordService = passwordService;
        this.jwtConfig = jwtConfig;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePassword updatePassword,
                                                 HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                String email = jwtConfig.getUsernameFromToken(token);
                if (jwtConfig.validateToken(token)) {
                    passwordService.updatePassword(email, updatePassword);
                    return ResponseEntity.status(HttpStatus.OK).body("Update password successful");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error while updating the password: " + e.getMessage());
        }
    }
}
