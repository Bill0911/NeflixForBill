package com.example.netflix.controller;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.service.GenreForSeriesService;
import com.example.netflix.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre-for-series")
public class GenreForSeriesController
{

    private final GenreForSeriesService genreForSeriesService;
    private final JwtUtil jwtUtil;

    public GenreForSeriesController (GenreForSeriesService genreForSeriesService, JwtUtil jwtUtil)
    {
        this.genreForSeriesService = genreForSeriesService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<GenreForSeries> createGenreForSeries (@RequestBody GenreForSeries genreForSeries)
    {
        GenreForSeries createdGenreForSeries = genreForSeriesService.save(genreForSeries);
        return ResponseEntity.ok(createdGenreForSeries);
    }

    @GetMapping("/{genreId}/{seriesId}")
    public ResponseEntity<GenreForSeries> getGenreForSeries (@PathVariable Integer genreId, @PathVariable Integer seriesId)
    {
        GenreForSeriesId id = new GenreForSeriesId(genreId, seriesId);
        GenreForSeries genreForSeries = genreForSeriesService.findById(id);
        return ResponseEntity.ok(genreForSeries);
    }

    @GetMapping
    public ResponseEntity<List<GenreForSeries>> getAllGenreForSeries ()
    {
        List<GenreForSeries> genreForSeriesList = genreForSeriesService.findAll();
        return ResponseEntity.ok(genreForSeriesList);
    }

    @PutMapping("/{genreId}/{seriesId}")
    public ResponseEntity<GenreForSeries> updateGenreForSeries (@PathVariable Integer genreId, @PathVariable Integer seriesId, @RequestBody GenreForSeries genreForSeries)
    {
        GenreForSeriesId id = new GenreForSeriesId(genreId, seriesId);
        GenreForSeries updatedGenreForSeries = genreForSeriesService.update(id, genreForSeries);
        return ResponseEntity.ok(updatedGenreForSeries);
    }

    @DeleteMapping("/{genreId}/{seriesId}")
    public ResponseEntity<Void> deleteGenreForSeries (@PathVariable Integer genreId, @PathVariable Integer seriesId)
    {
        GenreForSeriesId id = new GenreForSeriesId(genreId, seriesId);
        genreForSeriesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

