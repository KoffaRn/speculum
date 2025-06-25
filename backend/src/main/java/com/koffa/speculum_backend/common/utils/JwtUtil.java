package com.koffa.speculum_backend.common.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {
    @Autowired
    private Environment environment;
    public String generateToken(String username, String role) {
        Date now = new Date(System.currentTimeMillis());

        long expirationTime = 1000 * 60 * 25;
        return Jwts.builder()
                .claim("role", role)
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Objects.requireNonNull(environment.getProperty("jwt.token.secret")).getBytes(StandardCharsets.UTF_8));
    }
}
