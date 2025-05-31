package com.example.netflix.controller;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(new ResponseItem("New movie inserted/added", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMovieById(@PathVariable Integer id) {
        try {
            movieService.deleteMovieById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchMovieById(@PathVariable Integer id, @RequestBody Movie movie) {
        try {
            movieService.patchMovieById(id, movie);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMovieById(@PathVariable Integer id, @RequestBody Movie updatedMovie) {
        try {
            movieService.updateMovieById(id, updatedMovie);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @GetMapping("filtering")
    public ResponseEntity<Object> getMoviesWithoutGenre(@RequestParam boolean hasGenre) {
        if (!hasGenre) {
            return ResponseEntity.ok(movieService.getMoviesWithoutGenre());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).
                    body(new ResponseItem("return for 'hasGenre=true' not implemented", HttpStatus.NOT_IMPLEMENTED));
        }
    }
}
