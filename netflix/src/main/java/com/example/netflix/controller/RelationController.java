package com.example.netflix.controller;
import com.example.netflix.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/relations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class RelationController {
    private final GenreForMovieService genreForMovieService;
    private final GenreForSeriesService genreForSeriesService;
    private final GenreForUserService genreForUserService;
    private final  MoviesProfileWatchlistService moviesProfileWatchlistService;
    private final MovieViewCountService movieViewCountService;
    private final SeriesProfileWatchlistService seriesProfileWatchlistService;
    private final SeriesViewCountService seriesViewCountService;

    public RelationController(GenreForMovieService genreForMovieService, GenreForSeriesService genreForSeriesService, GenreForUserService genreForUserService, MoviesProfileWatchlistService moviesProfileWatchlistService, MovieViewCountService movieViewCountService, SeriesProfileWatchlistService seriesProfileWatchlistService, SeriesViewCountService seriesViewCountService) {
        this.genreForMovieService = genreForMovieService;
        this.genreForSeriesService = genreForSeriesService;
        this.genreForUserService = genreForUserService;
        this.moviesProfileWatchlistService = moviesProfileWatchlistService;
        this.movieViewCountService = movieViewCountService;
        this.seriesProfileWatchlistService = seriesProfileWatchlistService;
        this.seriesViewCountService = seriesViewCountService;
    }

    @GetMapping("/genre-movie")
    public ResponseEntity<Object> getManyGenreForMovie() {
        return ResponseEntity.ok(genreForMovieService.getManyGenreForMovies());
    }

    @GetMapping("/genre-series")
    public ResponseEntity<Object> getManyGenreForSeries() {
        return ResponseEntity.ok(genreForSeriesService.getManyGenreForSeries());
    }

    @GetMapping("/genre-user")
    public ResponseEntity<Object> getManyGenreForUser() {
        return ResponseEntity.ok(genreForUserService.getManyGenreForUsers());
    }

    @GetMapping("/movie-profile")
    public ResponseEntity<Object> getManyMoviesProfileWatchlists() {
        return ResponseEntity.ok(moviesProfileWatchlistService.getManyMoviesProfileWatchlists());
    }

    @GetMapping("/movie-user")
    public ResponseEntity<Object> getManyMovieViewCounts() {
        return ResponseEntity.ok(movieViewCountService.getManyMovieViewCounts());
    }

    @GetMapping("/series-profile")
    public ResponseEntity<Object> getManySeriesProfileWatchlists() {
        return ResponseEntity.ok(seriesProfileWatchlistService.getManySeriesProfileWatchlists());
    }

    @GetMapping("/series-user")
    public ResponseEntity<Object> getManySeriesViewCounts() {
        return ResponseEntity.ok(seriesViewCountService.getManySeriesViewCounts());
    }
}