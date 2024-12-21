package com.example.netflix.controller;

import com.example.netflix.entity.Series;
import com.example.netflix.service.SeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping
    public ResponseEntity<List<Series>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getAllSeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> getSeriesById(@PathVariable Integer id) {
        return seriesService.getSeriesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Series> createSeries(@RequestBody Series series) {
        return ResponseEntity.ok(seriesService.createSeries(series));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Series> updateSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            return ResponseEntity.ok(seriesService.updateSeries(id, series));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeries(@PathVariable Integer id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok("Series deleted successfully.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Series> patchSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            return ResponseEntity.ok(seriesService.patchSeries(id, series));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
