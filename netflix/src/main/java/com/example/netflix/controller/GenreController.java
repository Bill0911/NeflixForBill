package com.example.netflix.controller;

import com.example.netflix.entity.Genre;
import com.example.netflix.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController
{
    private final GenreService genreService;

    public GenreController(GenreService genreService)
    {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres()
    {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Integer id)
    {
        return genreService.getGenreById(id);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre)
    {
        genreService.createGenre(genre);
        return ResponseEntity.ok(genre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Integer id, @RequestBody Genre genre)
    {
        try
        {
            return ResponseEntity.ok(genreService.updateGenre(id, genre));
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Integer id)
    {
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Genre deleted successfully.");
    }
}
