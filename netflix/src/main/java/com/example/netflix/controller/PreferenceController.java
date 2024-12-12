package com.example.netflix.controller;

import com.example.netflix.dto.MethodResponse;
import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.MovieViewCountService;
import com.example.netflix.service.ProfileService;
import com.example.netflix.service.SeriesViewCountService;
import com.example.netflix.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {
    private final UserService userService;

    private final ProfileService profileService;

    private final MovieViewCountService movieViewCountService;
    private final SeriesViewCountService seriesViewCountService;
    private final JwtUtil jwtUtil;

    public PreferenceController(UserService userService, ProfileService profileService, MovieViewCountService movieViewCountService, SeriesViewCountService seriesViewCountService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.profileService = profileService;
        this.movieViewCountService = movieViewCountService;
        this.seriesViewCountService = seriesViewCountService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/chosen-genres")
    public ResponseEntity<GenreForSeries> getByChosenGenres ()
    {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/previously-watched")
    public ResponseEntity<GenreForSeries> getByPreviouslyWatched ()
    {
        return ResponseEntity.ok(null);
    }
}