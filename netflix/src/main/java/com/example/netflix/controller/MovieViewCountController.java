package com.example.netflix.controller;

import com.example.netflix.service.MovieViewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie-view-count")
public class MovieViewCountController {

    private final MovieViewCountService movieViewCountService;

    public MovieViewCountController(MovieViewCountService movieViewCountService) {
        this.movieViewCountService = movieViewCountService;
    }

    @PostMapping("/increment")
    public ResponseEntity<String> incrementViewCount(@RequestParam Integer userId, @RequestParam Integer movieId) {
        movieViewCountService.incrementMovieViewCount(userId, movieId);
        return ResponseEntity.ok("View count incremented successfully");
    }
}
