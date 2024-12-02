package com.example.netflix.controller;

import com.example.netflix.service.MovieViewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieViewCountController {

    private final MovieViewCountService movieViewCountService;

    public MovieViewCountController(MovieViewCountService movieViewCountService) {
        this.movieViewCountService = movieViewCountService;
    }

    @PostMapping("/increment-view-count")
    public ResponseEntity<String> incrementViewCount(@RequestParam Integer accountId, @RequestParam Integer movieId) {
        movieViewCountService.incrementViewCount(accountId, movieId);
        return ResponseEntity.ok("View count updated successfully");
    }
}

