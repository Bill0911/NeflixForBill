package com.example.netflix.controller;

import com.example.netflix.entity.ResponseItem;
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
    public ResponseEntity<Object> addMoviesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer movieId) {
        try {
            moviesProfileWatchlistService.addMoviesProfileWatchlist(profileId, movieId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseItem("Relation has been created", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseItem.errorCheckForRelationItemsPOST(e.getMessage());
        }
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Object> getMoviesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer movieId) {
        if (moviesProfileWatchlistService.getMoviesProfileWatchlist(profileId, movieId) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Profile " + profileId + " - Movie " + movieId + " relation exists");
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Object> deleteMoviesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer movieId) {
        try {
            moviesProfileWatchlistService.deleteMoviesProfileWatchlist(profileId, movieId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }
}
