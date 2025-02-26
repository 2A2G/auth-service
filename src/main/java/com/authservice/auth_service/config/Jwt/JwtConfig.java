package com.authservice.auth_service.config.Jwt;

import com.authservice.auth_service.config.JwtBlacklistService;
import com.authservice.auth_service.entity.Permission;
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
import java.util.stream.Collectors;

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
                .claim("permissions", user.getRole().getPermissions()
                        .stream()
                        .map(Permission::getName_permission)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (JwtException e) {
            System.out.println("Error parsing token: " + e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !jwtBlacklistService.isTokenBlacklisted(token);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Error validating token: " + e.getMessage());
            //  e.printStackTrace();
            return false;
        }
    }

}
