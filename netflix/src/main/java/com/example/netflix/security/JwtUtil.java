package com.example.netflix.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(int accountId)
    {
        System.out.println("Generating token for account ID: " + accountId);
        Map<String, Object> claims = new HashMap<>();
        claims.put("account_id", accountId); // Add account ID as a claim
        String token = createToken(claims);
        System.out.println("Token generated successfully for account ID: " + accountId);
        return token;
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiry (10 hours)
                .signWith(SignatureAlgorithm.HS256, secretKey) // Signing key
                .compact();
    }

    // Extract account_id from the token
    public int extractId(String token)
    {
        System.out.println("Extracting account ID from token");
        Claims claims = extractAllClaims(token);
        int accountID = (int) claims.get("account_id");
        System.out.println("Extracted account ID: " + accountID);
        return accountID;
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        System.out.println("Extracting all claims from token");
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
