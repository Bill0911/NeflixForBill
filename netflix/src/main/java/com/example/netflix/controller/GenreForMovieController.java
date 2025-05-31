package com.example.netflix.controller;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.GenreForMovieService;
import com.example.netflix.service.GenreService;
import com.example.netflix.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/movies/{movieId}/genres",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class GenreForMovieController {
    private GenreService genreService;
    private MovieService movieService;
    private GenreForMovieService genreForMovieService;

    public GenreForMovieController(GenreService genreService, MovieService movieService, GenreForMovieService genreForMovieService) {
        this.genreService = genreService;
        this.movieService = movieService;
        this.genreForMovieService = genreForMovieService;
    }

    @PostMapping("/{genreId}")
    public ResponseEntity<Object> addGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        try {
            genreService.getGenreById(genreId);
            movieService.getMovieById(movieId);
            genreForMovieService.addGenreForMovie(genreId, movieId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Genre - Movie relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Object> getGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        if (genreForMovieService.getGenreForMovie(genreId, movieId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such relation found");
        }

        return ResponseEntity.ok("Genre " + genreId + " - Movie " + movieId + " relation exists");
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Object> deleteGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        try {
            genreForMovieService.deleteGenreForMovie(genreId, movieId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }
}
