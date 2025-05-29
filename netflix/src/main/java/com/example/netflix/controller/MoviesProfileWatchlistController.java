package com.example.netflix.controller;

import com.example.netflix.service.MovieService;
import com.example.netflix.service.MoviesProfileWatchlistService;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movie-watchlist", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class MoviesProfileWatchlistController
{
    private ProfileService profileService;
    private MovieService movieService;
    private MoviesProfileWatchlistService moviesProfileWatchlistService;

    public MoviesProfileWatchlistController(ProfileService profileService, MovieService movieService, MoviesProfileWatchlistService moviesProfileWatchlistService) {
        this.profileService = profileService;
        this.movieService = movieService;
        this.moviesProfileWatchlistService = moviesProfileWatchlistService;
    }

    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<Object> addMoviesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            profileService.getProfileById(id1);
            movieService.getMovieById(id2);
            
            boolean exists = moviesProfileWatchlistService.existsByProfileIdAndMovieId(id1, id2);
            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: The profile-movie relation already exists");
            }

            moviesProfileWatchlistService.addMoviesProfileWatchlist(id1, id2);
            return ResponseEntity.ok("Profile - Movie relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Object> getMoviesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2) {
        if (moviesProfileWatchlistService.getMoviesProfileWatchlist(id1, id2) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Profile " + id1 + " - Movie " + id2 + " relation exists");
    }

    @GetMapping()
    public ResponseEntity<Object> getManyMoviesProfileWatchlists() {
        return ResponseEntity.ok(moviesProfileWatchlistService.getManyMoviesProfileWatchlists());
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Object> deleteMoviesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            moviesProfileWatchlistService.deleteMoviesProfileWatchlist(id1, id2);
            return ResponseEntity.ok("Profile - Movie relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
