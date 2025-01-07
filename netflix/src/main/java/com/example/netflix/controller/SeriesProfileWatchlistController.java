package com.example.netflix.controller;

import com.example.netflix.service.SeriesService;
import com.example.netflix.service.SeriesProfileWatchlistService;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series-watchlist")
public class SeriesProfileWatchlistController {
    private ProfileService profileService;
    private SeriesService seriesService;
    private SeriesProfileWatchlistService seriesProfileWatchlistService;

    public SeriesProfileWatchlistController(ProfileService profileService, SeriesService seriesService, SeriesProfileWatchlistService seriesProfileWatchlistService) {
        this.profileService = profileService;
        this.seriesService = seriesService;
        this.seriesProfileWatchlistService = seriesProfileWatchlistService;
    }

    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<Object> addSeriesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            profileService.getProfileById(id1);
            seriesService.getSeriesById(id2);
            seriesProfileWatchlistService.addSeriesProfileWatchlist(id1, id2);
            return ResponseEntity.ok("Profile - series relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Object> getSeriesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2) {
        if (seriesProfileWatchlistService.getSeriesProfileWatchlist(id1, id2) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Profile " + id1 + " - Series " + id2 + " relation exists");
    }

    @GetMapping()
    public ResponseEntity<Object> getManySeriesProfileWatchlists() {
        return ResponseEntity.ok(seriesProfileWatchlistService.getManySeriesProfileWatchlists());
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Object> deleteSeriesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            seriesProfileWatchlistService.deleteSeriesProfileWatchlist(id1, id2);
            return ResponseEntity.ok("Profile - Series relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> patchSeriesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            if (newId1 == 0) {
                newId1 = null;
            }
            if (newId2 == 0) {
                newId2 = null;
            }
            seriesProfileWatchlistService.patchSeriesProfileWatchlist(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> putSeriesProfileWatchlist(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            seriesProfileWatchlistService.updateSeriesProfileWatchlist(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}