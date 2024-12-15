package com.example.netflix.controller;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Series;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PersonalizedOfferController {
    private final PreferenceService PreferenceService;
    private final JwtUtil jwtUtil;

    public PersonalizedOfferController(com.example.netflix.service.PreferenceService preferenceService, JwtUtil jwtUtil) {
        PreferenceService = preferenceService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/chosen-genres/movies")
    public ResponseEntity<List<Movie>> getMoviesByChosenGenres ()
    {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/chosen-genres/series")
    public ResponseEntity<List<Series>> getSeriesByChosenGenres ()
    {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/previously-watched/movies")
    public ResponseEntity<List<Movie>> getMoviesByPreviouslyWatched ()
    {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/previously-watched/series")
    public ResponseEntity<List<Series>> getSeriesByPreviouslyWatched ()
    {
        return ResponseEntity.ok(null);
    }
}