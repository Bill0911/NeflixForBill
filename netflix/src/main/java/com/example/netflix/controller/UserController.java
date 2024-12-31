package com.example.netflix.controller;

import com.example.netflix.dto.LoginRequest;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.User;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Invalid Authorization header");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Assuming loginUser now returns both userId and role
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(user.getAccountId(), user.getRole().name()); // Pass accountId and role
            System.out.println("Token generated successfully for user with email: " + loginRequest.getEmail());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            System.out.println("Login failed for email: " + loginRequest.getEmail() + " - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login failed: " + e.getMessage());
        }
    }
    @GetMapping("/lang")
    public ResponseEntity<String> getLanguage(@RequestHeader("Authorization") String token) {
        try {
            int id = jwtUtil.extractId(extractToken(token));
            String language = userService.getLanguageName(id);
            return ResponseEntity.ok("Language: " + language);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/lang")
    public ResponseEntity<String> setLanguage(@RequestParam Integer languageId, @RequestHeader("Authorization") String token) {
        try {
            int id = jwtUtil.extractId(extractToken(token));
            String language = userService.changeLanguage(languageId, id);
            return ResponseEntity.ok("Language set to " + language);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/add-profile")
    public ResponseEntity<String> addProfile(@RequestBody ProfileRequest profileRequest, @RequestHeader("Authorization") String token) {
        try {
            int id = jwtUtil.extractId(extractToken(token));
            Profile profile = userService.addProfile(profileRequest, id);
            return ResponseEntity.ok(profile.getName() + " has been added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email)
    {
        try {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetToken(email, token);

            // Log the reset token to the console (simulating email sending)
            System.out.println("Password reset token for " + email + ": " + token);

            return ResponseEntity.ok("Password reset token has been generated. Check the console for the token.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword)
    {
        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
