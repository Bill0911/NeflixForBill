package com.example.netflix.controller;

import com.example.netflix.service.SeriesService;
import com.example.netflix.service.GenreForSeriesService;
import com.example.netflix.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/genre-for-series", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class GenreForSeriesController {
    private GenreService genreService;
    private SeriesService seriesService;
    private GenreForSeriesService genreForSeriesService;

    public GenreForSeriesController(GenreService genreService, SeriesService seriesService, GenreForSeriesService genreForSeriesService) {
        this.genreService = genreService;
        this.seriesService = seriesService;
        this.genreForSeriesService = genreForSeriesService;
    }

    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<Object> addGenreForSeries(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            genreService.getGenreById(id1);
            seriesService.getSeriesById(id2);
            genreForSeriesService.addGenreForSeries(id1, id2);
            return ResponseEntity.ok("Genre - series relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Object> getGenreForSeries(@PathVariable Integer id1, @PathVariable Integer id2) {
        if (genreForSeriesService.getGenreForSeries(id1, id2) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Genre " + id1 + " - Series " + id2 + " relation exists");
    }

    @GetMapping()
    public ResponseEntity<Object> getManyGenreForSeries() {
        return ResponseEntity.ok(genreForSeriesService.getManyGenreForSeries());
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Object> deleteGenreForSeries(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            genreForSeriesService.deleteGenreForSeries(id1, id2);
            return ResponseEntity.ok("Genre - Series relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}