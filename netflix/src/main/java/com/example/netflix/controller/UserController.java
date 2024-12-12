package com.example.netflix.controller;

import com.example.netflix.dto.LoginRequest;
import com.example.netflix.dto.SubscriptionOverview;
import com.example.netflix.entity.Profile;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.entity.User;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        int id = jwtUtil.extractId(token.substring(7));
        String language = userService.getLanguageName(id);
        return ResponseEntity.ok("Language: " + language);
    }

    @PostMapping("/lang")
    public ResponseEntity<String> setLanguage(@RequestParam Integer languageId, @RequestHeader("Authorization") String token) {
        int id = jwtUtil.extractId(token.substring(7));
        String language = userService.changeLanguage(languageId, id);
        if (!language.equals("none")) {
            return ResponseEntity.ok("Language set to " + language);
        }

        return ResponseEntity.ok("Invalid language or user");
    }

    @PostMapping("/add-profile")
    public ResponseEntity<String> addProfile(@RequestBody ProfileRequest profileRequest, @RequestHeader("Authorization") String token) {
        int id = jwtUtil.extractId(token.substring(7));
        Profile profile = userService.addProfile(profileRequest, id);
        if (profile != null)
        {
            return ResponseEntity.ok("{" + profile.getName() + "} has been added successfully");
        }

        return ResponseEntity.ok("Nothing has been added");
    }

//    @GetMapping("/view/subscription-overview")
//    public List<SubscriptionOverview> getSubscriptionOverview() {
//        return userService.getAllSubscriptions();
//    }
}
