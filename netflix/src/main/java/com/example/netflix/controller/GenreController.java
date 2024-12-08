package com.example.netflix.controller;

import com.example.netflix.entity.Genre;
import com.example.netflix.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController
{

    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.createGenre(genre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Integer id, @RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.updateGenre(id, genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Integer id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Genre deleted successfully.");
    }
}
