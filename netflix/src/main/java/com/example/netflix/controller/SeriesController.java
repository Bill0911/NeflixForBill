package com.example.netflix.controller;

import com.example.netflix.entity.Series;
import com.example.netflix.service.SeriesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/series", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping
    public ResponseEntity<List<Series>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getManySeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> getSeriesById(@PathVariable Integer id) {
        Series series = seriesService.getSeriesById(id);
        if (series == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(series);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addSeries(@RequestBody Series series) {
        try {
            seriesService.addSeries(series);
            return ResponseEntity.ok("Series has been created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Series> updateSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            Series updatedSeries = seriesService.updateSeriesById(id, series);
            return ResponseEntity.ok(updatedSeries);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeries(@PathVariable Integer id) {
        try {
            seriesService.deleteSeriesById(id);
            return ResponseEntity.ok("Series deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Series not found.");
        }
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Series> patchSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            Series patchedSeries = seriesService.patchSeriesById(id, series);
            return ResponseEntity.ok(patchedSeries);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
