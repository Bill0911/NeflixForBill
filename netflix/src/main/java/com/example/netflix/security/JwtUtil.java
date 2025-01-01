package com.example.netflix.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 3600000; // 1 hour in milliseconds

    //========login token======//
    public String generateToken(int accountId, String role) {
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
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
