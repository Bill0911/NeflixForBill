package com.example.netflix.controller;

import com.example.netflix.entity.Series;
import com.example.netflix.service.SeriesService;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(seriesService.getManySeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> getSeriesById(@PathVariable Integer id) {
        return ResponseEntity.ok(seriesService.getSeriesById(id));
    }

    @PostMapping
    public ResponseEntity<Object> addSeries(@RequestBody Series series) {
        try {
            seriesService.addSeries(series);
            return ResponseEntity.ok("Series has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            seriesService.updateSeriesById(id, series);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("tried");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeries(@PathVariable Integer id) {
        seriesService.deleteSeriesById(id);
        return ResponseEntity.ok("Series deleted successfully.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            seriesService.patchSeriesById(id, series);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Tried");
    }
}
