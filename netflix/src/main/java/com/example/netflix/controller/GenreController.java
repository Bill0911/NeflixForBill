package com.example.netflix.controller;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.entity.Genre;
import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
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
    public ResponseEntity<Object> addGenre(@RequestBody GenreDTO genre) {
        genreService.addGenre(genre.getGenreName());
        return ResponseEntity.ok(new ResponseItem("New genre inserted/added", HttpStatus.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGenre(@PathVariable Integer id, @RequestBody GenreDTO genre) {
        try {
            genreService.updateGenre(id, genre.getGenreName());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchGenre(@PathVariable Integer id, @RequestBody GenreDTO genre) {
        try {
            genreService.updateGenre(id, genre.getGenreName());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGenre(@PathVariable Integer id) {
        try {
            genreService.deleteGenre(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        }
        catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }

    @GetMapping("/filtering")
    public ResponseEntity<Object> getGenresWithoutMovie(@RequestParam boolean hasMovie) {
        if (!hasMovie) {
            return ResponseEntity.ok(genreService.getGenresWithoutMovie());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).
                    body(new ResponseItem("return for 'hasMovie=true' not implemented", HttpStatus.NOT_IMPLEMENTED));
        }
    }

    @GetMapping("/statistics/views")
    public ResponseEntity<Object> getGenresViewCounts() {
        return ResponseEntity.ok(genreService.getViewCounts());
    }
}
