package com.authservice.auth_service.config;

import com.authservice.auth_service.config.Jwt.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JwtBlacklistService {

    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.secret}")
    private String secretKey;

    private final List<String> blacklistedTokens = new ArrayList<>();

    public JwtBlacklistService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ResponseEntity<String> addToBlacklist(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expirationFromToken = claims.getExpiration();
            long expirationTime = expirationFromToken.getTime() - System.currentTimeMillis();

            if (expirationTime > 0) {
                // Comentando la línea de Redis
                // redisTemplate.opsForValue().set(token, "blacklisted", expirationTime, TimeUnit.MILLISECONDS);

                // Agregando el token a la lista en memoria
                blacklistedTokens.add(token);

                return ResponseEntity.ok("Token agregado a la blacklist");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token ya expirado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar a la blacklist: " + e.getMessage());
        }
    }

    public boolean isTokenBlacklisted(String token) {
        try {
            if (blacklistedTokens.contains(token)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al verificar blacklist: " + e.getMessage());
            return false;
        }
    }


}
