package com.authservice.auth_service.controller;

import com.authservice.auth_service.config.JwtBlacklistService;
import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.request.LoginRequest;
import com.authservice.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-auth")
public class LoginController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtBlacklistService jwtBlacklistService;

    public LoginController(UserService userService, JwtBlacklistService jwtBlacklistService) {
        this.userService = userService;
        this.jwtBlacklistService = jwtBlacklistService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> accessLogin(@RequestBody LoginRequest user) {
        try {
            String token = userService.accessLogin(user);
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al procesar el login: " + e.getMessage());
        }
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            String message = userService.newUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al registrar el usuario");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(String token) {
        try {
            if (!(token == null)) {
                return userService.logout(token);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error inesperado al logout. Falta el Token");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al hacer logout");
        }
    }
}
