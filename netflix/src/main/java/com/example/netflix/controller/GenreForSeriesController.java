package com.example.netflix.controller;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.service.GenreForSeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genreforseries")
public class GenreForSeriesController {

    private final GenreForSeriesService genreForSeriesService;

    public GenreForSeriesController(GenreForSeriesService genreForSeriesService) {
        this.genreForSeriesService = genreForSeriesService;
    }

    @GetMapping
    public ResponseEntity<List<GenreForSeries>> getAllGenreForSeries() {
        return ResponseEntity.ok(genreForSeriesService.getAll());
    }

    @PostMapping
    public ResponseEntity<GenreForSeries> addGenreForSeries(@RequestParam Integer seriesId, @RequestParam Integer genreId) {
        return ResponseEntity.ok(genreForSeriesService.addGenreForSeries(seriesId, genreId));
    }

    @PatchMapping
    public ResponseEntity<GenreForSeries> patchGenreForSeries(@RequestParam Integer seriesId, @RequestParam Integer oldGenreId, @RequestParam Integer newGenreId) {
        return ResponseEntity.ok(genreForSeriesService.patchGenreForSeries(seriesId, oldGenreId, newGenreId));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteGenreForSeries(@RequestParam Integer seriesId, @RequestParam Integer genreId) {
        genreForSeriesService.deleteGenreForSeries(seriesId, genreId);
        return ResponseEntity.ok("GenreForSeries relationship deleted successfully.");
    }
}
