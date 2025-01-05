package com.example.netflix.controller;

import com.example.netflix.entity.Movie;
import com.example.netflix.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<Object> getAllMovies() {
        return ResponseEntity.ok(movieService.getManyMovies());
    }


    @PostMapping
    public ResponseEntity<Object> addMovie(@RequestBody Movie movie) {
        try {
            movieService.addMovie(movie);
            return ResponseEntity.ok("Movie has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Integer id) {
        try {
            movieService.deleteMovieById(id);
            return ResponseEntity.ok("Movie deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchMovieById(@PathVariable Integer id, @RequestBody Movie movie) {
        try {
            movieService.patchMovieById(id, movie);
            return ResponseEntity.ok("Movie has been patched successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMovieById(@PathVariable Integer id, @RequestBody Movie updatedMovie) {
        try {
            movieService.updateMovieById(id, updatedMovie);
            return ResponseEntity.ok("Movie has been updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
