package com.example.netflix.controller;

import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.SeriesService;
import com.example.netflix.service.GenreForSeriesService;
import com.example.netflix.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/series/{seriesId}/genre", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class GenreForSeriesController {
    private GenreService genreService;
    private SeriesService seriesService;
    private GenreForSeriesService genreForSeriesService;

    public GenreForSeriesController(GenreService genreService, SeriesService seriesService, GenreForSeriesService genreForSeriesService) {
        this.genreService = genreService;
        this.seriesService = seriesService;
        this.genreForSeriesService = genreForSeriesService;
    }

    @PostMapping("/{genreId}")
    public ResponseEntity<Object> addGenreForSeries(@PathVariable Integer genreId, @PathVariable Integer seriesId) {
        try {
            genreService.getGenreById(genreId);
            seriesService.getSeriesById(seriesId);
            genreForSeriesService.addGenreForSeries(genreId, seriesId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Genre - series relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Object> getGenreForSeries(@PathVariable Integer genreId, @PathVariable Integer seriesId) {
        if (genreForSeriesService.getGenreForSeries(genreId, seriesId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such relation found");
        }

        return ResponseEntity.ok("Genre " + genreId + " - Series " + seriesId + " relation exists");
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Object> deleteGenreForSeries(@PathVariable Integer genreId, @PathVariable Integer seriesId) {
        try {
            genreForSeriesService.deleteGenreForSeries(genreId, seriesId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }
}