package com.authservice.auth_service.controller;

import com.authservice.auth_service.config.Jwt.JwtConfig;
import com.authservice.auth_service.config.JwtBlacklistService;
import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.request.LoginRequest;
import com.authservice.auth_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api-auth")
public class LoginController {
    private final UserService userService;
    private final JwtConfig jwtConfig;

    public LoginController(UserService userService, JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
    }


    @PostMapping("/api-auth/login")
    public ResponseEntity<?> accessLogin(@RequestBody LoginRequest user) {
        try {
            String token = userService.accessLogin(user);
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error while processing login: " + e.getMessage());
        }
    }

    @PostMapping("/api-auth/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            String message = userService.newUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error while registering the user.");
        }
    }

    @PostMapping("api-serve/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                if (jwtConfig.validateToken(token)) {
                    userService.logout(token);
                    return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out.");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Unexpected error while logging out. Token is missing.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error while logging out.");
        }
    }

}
