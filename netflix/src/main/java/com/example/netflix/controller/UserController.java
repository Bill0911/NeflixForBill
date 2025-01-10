package com.example.netflix.controller;

import com.example.netflix.dto.LoginRequest;
import com.example.netflix.entity.Role;
import com.example.netflix.entity.User;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(user.getAccountId(), user.getRole().name());

            // Return token and role
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", user.getRole().name(),
                    "message", "Login successful!"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "error", e.getMessage(),
                    "message", "Login failed. Check credentials."
            ));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getManyUsers());
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            // Register new user
            User registeredUser = userService.register(user);

            // Generate activation token
            String token = jwtUtil.generateActivationToken(registeredUser.getEmail());
            String activationLink = "http://localhost:8081/api/users/activate?token=" + token;

            return ResponseEntity.ok(Map.of("activationLink", activationLink));
        } catch (RuntimeException e) {
            // Return a structured error response for registration failure
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activateUser(@RequestParam String token) {
        try {
            String email = jwtUtil.extractEmail(token);
            userService.activateUser(email);
            return ResponseEntity.ok("User activated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage(),
                    "message", "Activation failed. Invalid or expired token."
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping()
    public ResponseEntity<Object> getManyUsers() {
        return ResponseEntity.ok(userService.getManyUsers());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Integer id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User has been deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> patchUserById(@PathVariable Integer id, @RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            userService.enforceRoleRestriction(token, Role.SENIOR);
            userService.patchUserById(id, user);
            return ResponseEntity.ok("User has been patched successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> putUserById(@PathVariable Integer id, @RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            userService.enforceRoleRestriction(token, Role.SENIOR);
            userService.updateUserById(id, user);
            return ResponseEntity.ok("User has been updated successfully");
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
            System.out.println("Password reset token: " + token);
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
}
