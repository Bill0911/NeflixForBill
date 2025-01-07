package com.example.netflix.controller;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.service.GenreForMovieService;
import com.example.netflix.service.GenreService;
import com.example.netflix.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre-for-movie")
public class GenreForMovieController {
    private GenreService genreService;
    private MovieService movieService;
    private GenreForMovieService genreForMovieService;

    public GenreForMovieController(GenreService genreService, MovieService movieService, GenreForMovieService genreForMovieService) {
        this.genreService = genreService;
        this.movieService = movieService;
        this.genreForMovieService = genreForMovieService;
    }

    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<Object> addGenreForMovie(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            genreService.getGenreById(id1);
            movieService.getMovieById(id2);
            genreForMovieService.addGenreForMovie(id1, id2);
            return ResponseEntity.ok("Genre - Movie relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Object> getGenreForMovie(@PathVariable Integer id1, @PathVariable Integer id2) {
        if (genreForMovieService.getGenreForMovie(id1, id2) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Genre " + id1 + " - Movie " + id2 + " relation exists");
    }

    @GetMapping()
    public ResponseEntity<Object> getManyGenreForMovie() {
        return ResponseEntity.ok(genreForMovieService.getManyGenreForMovies());
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Object> deleteGenreForMovie(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            genreForMovieService.deleteGenreForMovie(id1, id2);
            return ResponseEntity.ok("Genre - Movie relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> patchGenreForMovie(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            if (newId1 == 0) {
                newId1 = null;
            }
            if (newId2 == 0) {
                newId2 = null;
            }
            genreForMovieService.patchGenreForMovie(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> putGenreForMovie(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            genreForMovieService.updateGenreForMovie(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
