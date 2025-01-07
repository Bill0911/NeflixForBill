package com.example.netflix.controller;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.MoviesProfileWatchlist;
import com.example.netflix.entity.Profile;
import com.example.netflix.service.MovieService;
import com.example.netflix.service.MoviesProfileWatchlistService;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie-watchlist")
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
            return ResponseEntity.ok("Movie - User relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> patchMoviesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            if (newId1 == 0) {
                newId1 = null;
            }
            if (newId2 == 0) {
                newId2 = null;
            }
            moviesProfileWatchlistService.patchMoviesProfileWatchlist(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> putMoviesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            moviesProfileWatchlistService.updateMoviesProfileWatchlist(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
