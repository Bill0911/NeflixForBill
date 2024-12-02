package com.example.netflix.controller;

import com.example.netflix.entity.LoginRequest;
import com.example.netflix.entity.User;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = jwtUtil.generateToken(userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()));
        return ResponseEntity.ok(token);
    }

    @GetMapping("/lang")
    public ResponseEntity<String> getLanguage(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        int id = jwtUtil.extractId(jwt);

        String language = userService.getLanguageName(id);
        return ResponseEntity.ok("Language: " + language);
    }
}
