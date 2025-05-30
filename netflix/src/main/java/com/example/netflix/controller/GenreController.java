package com.example.netflix.controller;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.entity.Genre;
import com.example.netflix.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/genres", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        return genreService.getGenreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> addGenre(@RequestBody GenreDTO genre) {
        genreService.addGenre(genre.getGenreName());
        return ResponseEntity.ok("New genre '"+ genre.getGenreName() +"' added");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenre(@PathVariable Integer id, @RequestBody GenreDTO genre) {
        try {
            genreService.updateGenre(id, genre.getGenreName());
            return ResponseEntity.ok("Genre id " + id + " has been renamed to " + genre.getGenreName());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to update genre with ID" + id + ": " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchGenre(@PathVariable Integer id, @RequestBody GenreDTO genre) {
        try {
            genreService.updateGenre(id, genre.getGenreName());
            return ResponseEntity.ok("Genre " + id + " has been renamed to " + genre.getGenreName());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to patch genre with ID" + id + ": " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Integer id) {
        try {
            genreService.deleteGenre(id);
            return ResponseEntity.ok("Genre deleted successfully.");
        }
        catch (RuntimeException e) {
            if (e.getMessage().contains("foreign key constraint")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This genres' id is mention in other table's rows as foreign key");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deletion failed: " + e.getMessage());
        }
    }

    @GetMapping("/filtering")
    public ResponseEntity<Object> getGenresWithoutMovie(@RequestParam boolean hasMovie) {
        if (!hasMovie) {
            return ResponseEntity.ok(genreService.getGenresWithoutMovie());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("return for 'hasMovie=true' not implemented");
        }
    }

    @GetMapping("/statistics/views")
    public ResponseEntity<Object> getGenresViewCounts() {
        return ResponseEntity.ok(genreService.getViewCounts());
    }
}
