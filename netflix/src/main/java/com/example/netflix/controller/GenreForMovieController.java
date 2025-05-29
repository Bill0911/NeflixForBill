package com.example.netflix.controller;

import com.example.netflix.entity.GenreForMovie;
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
            return ResponseEntity.ok("Genre - Movie relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Object> getGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        if (genreForMovieService.getGenreForMovie(genreId, movieId) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Genre " + genreId + " - Movie " + movieId + " relation exists");
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Object> deleteGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        try {
            genreForMovieService.deleteGenreForMovie(genreId, movieId);
            return ResponseEntity.ok("Genre - Movie relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
