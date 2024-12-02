package com.example.netflix.controller;

import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.MovieViewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieViewCountController {

    private final MovieViewCountService movieViewCountService;
    private final JwtUtil jwtUtil;

    public MovieViewCountController(MovieViewCountService movieViewCountService, JwtUtil jwtUtil) {
        this.movieViewCountService = movieViewCountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/increment-view-count")
    public ResponseEntity<String> incrementViewCount(@RequestParam Integer accountId, @RequestParam Integer movieId) {
        movieViewCountService.incrementViewCount(accountId, movieId);
        return ResponseEntity.ok("View count updated successfully");
    }


    @PostMapping("/add-to-viewcount")
    public ResponseEntity<String> addToViewCount(@RequestParam Integer movieId, @RequestHeader("Authorization") String token) {
        // Extract the JWT token without "Bearer "
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);

        movieViewCountService.addMovieToViewCount(username, movieId);
        return ResponseEntity.ok("Movie added to view count successfully!");
    }
}

