package com.example.netflix.controller;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.service.GenreForMovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genreformovies")
public class GenreForMovieController {

    private final GenreForMovieService genreForMovieService;

    public GenreForMovieController(GenreForMovieService genreForMovieService) {
        this.genreForMovieService = genreForMovieService;
    }

    @GetMapping
    public ResponseEntity<List<GenreForMovie>> getAllGenreForMovies() {
        return ResponseEntity.ok(genreForMovieService.getAll());
    }

    @PostMapping
    public ResponseEntity<GenreForMovie> addGenreForMovie(@RequestParam Integer movieId, @RequestParam Integer genreId) {
        return ResponseEntity.ok(genreForMovieService.addGenreForMovie(movieId, genreId));
    }

    @PatchMapping
    public ResponseEntity<GenreForMovie> patchGenreForMovie(@RequestParam Integer movieId, @RequestParam Integer oldGenreId, @RequestParam Integer newGenreId) {
        return ResponseEntity.ok(genreForMovieService.patchGenreForMovie(movieId, oldGenreId, newGenreId));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteGenreForMovie(@RequestParam Integer movieId, @RequestParam Integer genreId) {
        genreForMovieService.deleteGenreForMovie(movieId, genreId);
        return ResponseEntity.ok("GenreForMovie relationship deleted successfully.");
    }
}
