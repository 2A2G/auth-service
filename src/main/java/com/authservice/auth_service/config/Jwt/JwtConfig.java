package com.authservice.auth_service.config.Jwt;

import com.authservice.auth_service.config.JwtBlacklistService;
import com.authservice.auth_service.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Configuration
@Data
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    private final JwtBlacklistService jwtBlacklistService;


    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        if (user.getRole() == null) {
            throw new RuntimeException("El usuario no tiene un rol asignado.");
        }

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().getName_rol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
