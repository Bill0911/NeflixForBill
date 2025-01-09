package com.example.netflix.security;

import com.example.netflix.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key secretKey;

    @PostConstruct
    public void init() {
        try {
            logger.info("Initializing JwtUtil with secret and expiration: " + expiration);
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        } catch (Exception e) {
            logger.severe("Error initializing JwtUtil: " + e.getMessage());
            throw e;
        }
    }

    //========login token======//
    public String generateToken(int accountId, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("account_id", accountId);
        claims.put("role", role); // Add role to claims
        return createToken(claims);
    }

    public int extractId(String token) {
        Claims claims = extractAllClaims(token);
        return (int) claims.get("account_id");
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("role");
    }

    //========Activation token========//
    public String generateActivationToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return createToken(claims, 24 * 60 * 60 * 1000); // 24 hours expiration for activation token
    }

    //=======reset password token=======//
    public String generatePasswordResetToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        String token = createToken(claims, 24 * 60 * 60 * 1000); // 24 hours expiration for password reset token
        return token;
    }

    public String extractEmailFromPasswordResetToken(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("email");
    }

    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("email");
    }

    private String createToken(Map<String, Object> claims) {
        return createToken(claims, expiration);
    }

    private String createToken(Map<String, Object> claims, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired", e);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
