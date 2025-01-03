package com.example.netflix.controller;

import com.example.netflix.dto.LoginRequest;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.User;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
        try {
            String token = jwtUtil.generateActivationToken(user.getEmail());
            userService.registerUser(user, token);
            // Log the activation token to the console for testing purposes
            System.out.println("Activation token for " + user.getEmail() + ": " + token);
            // Return the token in the response for testing purposes
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String token) {
        try {
            String email = jwtUtil.extractEmail(token);
            userService.activateUser(email);
            return ResponseEntity.ok("User activated successfully.");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired.");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
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

    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            userService.requestPasswordReset(email);
            String token = jwtUtil.generatePasswordResetToken(email);
            //System.out.println("Password reset token: " + token);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String newPassword = request.get("newPassword");
            String email = jwtUtil.extractEmailFromPasswordResetToken(token);
            System.out.println("Email extracted from token: " + email);
            userService.resetPassword(email, newPassword);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/invite")
    public void inviteUser(@RequestParam Integer userId, @RequestParam Integer invitedAccountId)
    {
        userService.inviteUser(userId, invitedAccountId);
    }
}
