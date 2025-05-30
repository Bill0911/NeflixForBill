package com.example.netflix.controller;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.Profile;
import com.example.netflix.service.MovieService;
import com.example.netflix.service.MovieViewCountService;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movie/{movieId}/user", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class MovieViewCountController {

    private final MovieViewCountService movieViewCountService;
    private final MovieService movieService;
    private final UserService userService;

    public MovieViewCountController(MovieViewCountService movieViewCountService, MovieService movieService, UserService userService) {
        this.movieViewCountService = movieViewCountService;
        this.movieService = movieService;
        this.userService = userService;
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<String> addMovieViewCount(@PathVariable Integer accountId,  @PathVariable Integer movieId) {
        try {
            userService.getUserById(accountId);
            movieService.getMovieById(movieId);
            movieViewCountService.addMovieViewCount(accountId, movieId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movie - User relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getMovieViewCount(@PathVariable Integer accountId, @PathVariable Integer movieId) {
        return ResponseEntity.ok(movieViewCountService.getMovieViewCount(accountId, movieId));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Object> deleteMovieViewCount(@PathVariable Integer accountId, @PathVariable Integer movieId) {
        try {
            movieViewCountService.deleteMovieViewCount(accountId, movieId);
            return ResponseEntity.ok("Movie - User relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
