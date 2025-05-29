package com.example.netflix.controller;

import com.example.netflix.service.MovieService;
import com.example.netflix.service.MoviesProfileWatchlistService;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movie/{movieId}/profile", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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

    @PostMapping("/{profileId}")
    public ResponseEntity<Object> addMoviesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer id2) {
        try {
            profileService.getProfileById(profileId);
            movieService.getMovieById(id2);
            
            boolean exists = moviesProfileWatchlistService.existsByProfileIdAndMovieId(profileId, id2);
            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: The profile-movie relation already exists");
            }

            moviesProfileWatchlistService.addMoviesProfileWatchlist(profileId, id2);
            return ResponseEntity.ok("Profile - Movie relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Object> getMoviesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer id2) {
        if (moviesProfileWatchlistService.getMoviesProfileWatchlist(profileId, id2) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Profile " + profileId + " - Movie " + id2 + " relation exists");
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Object> deleteMoviesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer id2) {
        try {
            moviesProfileWatchlistService.deleteMoviesProfileWatchlist(profileId, id2);
            return ResponseEntity.ok("Profile - Movie relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
