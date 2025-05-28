package com.example.netflix.controller;

import com.example.netflix.dto.InviteUserRequest;
import com.example.netflix.dto.LoginRequest;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.dto.SubscriptionOverview;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.Role;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import com.example.netflix.exception.AccessDeniedException;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping( value = "/api/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            System.out.println("CHECKPOINT - 1: Received registration request for user: " + user.getEmail());
            System.out.println("Subscription: " + user.getSubscription()); // Log the subscription

            // Validate subscription value
            SubscriptionType subscription = user.getSubscription();
            if (subscription != SubscriptionType.SD && subscription != SubscriptionType.HD && subscription != SubscriptionType.UHD) {
                throw new RuntimeException("Invalid subscription type: " + subscription);
            }

            User registeredUser = userService.register(user);
            System.out.println("CHECKPOINT - 2: User registered successfully: " + registeredUser.getEmail());
            String token = jwtUtil.generateActivationToken(registeredUser.getEmail());
            System.out.println("CHECKPOINT - 3: Activation token generated: " + token);
            String activationLink = "http://localhost:8081/api/users/activate?token=" + token;
            System.out.println("CHECKPOINT - 4: Activation link generated: " + activationLink);

            return ResponseEntity.ok(Map.of(
                    "activationLink", activationLink
            ));
        } catch (RuntimeException e) {
            System.out.println("CHECKPOINT - error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activateUser(@RequestParam String token)
    {
        try
        {
            String email = jwtUtil.extractEmail(token);
            userService.activateUser(email);
            return ResponseEntity.ok("User activated successfully.");
        }
        catch (ExpiredJwtException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired.");
        }
        catch (JwtException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(user.getAccountId(), Role.valueOf(user.getRole().name()));

            // Create response payload
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole().name());
            response.put("message", "Login successful!");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "error", e.getMessage(),
                    "message", "Login failed. Please check your credentials."
            ));
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok("User has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws Exception {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping()
    public ResponseEntity<Object> getManyUsers() throws Exception {
        return ResponseEntity.ok(userService.getManyUsers());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Integer id) throws Exception {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User has been deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> patchUserById(@PathVariable Integer id, @RequestBody User user) throws Exception {
//        userService.enforceRoleRestriction(token, Role.SENIOR);
        System.out.println("role:" + user.getRole() + " and subs:" + user.getSubscription());
        userService.patchUserById(id, user);
        return ResponseEntity.ok("User has been updated successfully. Subscription should be set to ");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> putUserById(@PathVariable Integer id, @RequestBody User user) throws Exception {
//        userService.enforceRoleRestriction(token, Role.SENIOR);
        userService.updateUserById(id, user);
        return ResponseEntity.ok("User has been updated successfully");
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> request)
    {
        try
        {
            String email = request.get("email");
            userService.requestPasswordReset(email);
            String token = jwtUtil.generatePasswordResetToken(email);
            System.out.println("Password reset token: " + token);
            return ResponseEntity.ok(token);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) throws Exception
    {
        try
        {
            String token = request.get("token");
            String newPassword = request.get("newPassword");
            String email = jwtUtil.extractEmailFromPasswordResetToken(token);
            System.out.println("Email extracted from token: " + email);
            userService.resetPassword(email, newPassword);
            return ResponseEntity.ok("Password reset successfully.");
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/invite/{inviter}/{invitee}")
    public ResponseEntity<String> inviteUser(@PathVariable Integer inviter, @PathVariable Integer invitee) {
        try {
            userService.inviteUser(inviter, invitee);
            return ResponseEntity.ok("Invitation sent successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/subscription-costs")
    public ResponseEntity<List<SubscriptionOverview>> getSubscriptionCosts() throws Exception {
        return ResponseEntity.ok(userService.getAllSubscriptionCosts());
    }
}
