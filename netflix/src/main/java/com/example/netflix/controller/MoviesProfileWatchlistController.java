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
@RequestMapping("/api/movie-watchlist/")
public class MoviesProfileWatchlistController
{
    private ProfileService profileService;
    private MovieService movieService;
    private MoviesProfileWatchlistService moviesProfileWatchlistService;

    @PostMapping("/{accountId}/{movieId}")
    public ResponseEntity<String> addMovieViewCount(@PathVariable Integer accountId, @PathVariable Integer movieId) {
        try {
            userService.getUserById(accountId);
            movieService.getMovieById(movieId);
            moviesProfileWatchlistService.addMovieViewCount(accountId, movieId);
            return ResponseEntity.ok("Movie - User relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{accountId}/{movieId}")
    public ResponseEntity<Object> getMovieViewCount(@PathVariable Integer accountId, @PathVariable Integer movieId) {
        return ResponseEntity.ok(moviesProfileWatchlistService.getMovieViewCount(accountId, movieId));
    }

    @GetMapping()
    public ResponseEntity<Object> getManyMovieViewCounts() {
        return ResponseEntity.ok(moviesProfileWatchlistService.getManyMovieViewCounts());
    }

    @DeleteMapping("/{accountId}/{movieId}")
    public ResponseEntity<Object> deleteMovieViewCount(@PathVariable Integer accountId, @PathVariable Integer movieId) {
        try {
            moviesProfileWatchlistService.deleteMovieViewCount(accountId, movieId);
            return ResponseEntity.ok("Movie - User relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping()
    public ResponseEntity<Object> patchMovieViewCount(@RequestBody MovieViewCount movieViewCount) {
        try {
            System.out.println("CHECKPOINT - 1");
            moviesProfileWatchlistService.patch(movieViewCount);
            System.out.println("CHECKPOINT - 2");
            return ResponseEntity.ok(movieViewCount);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error: " + e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putMovieViewCount(@RequestBody MoviesProfileWatchlist moviesProfileWatchlist) {
        try {
            moviesProfileWatchlistService.updateMPW(moviesProfileWatchlist);
            return ResponseEntity.ok(moviesProfileWatchlist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error: " + e.getMessage());
        }
    }
}
