package com.example.netflix.controller;

import com.example.netflix.service.SeriesService;
import com.example.netflix.service.SeriesProfileWatchlistService;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/series/{series}/profile", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class SeriesProfileWatchlistController {
    private ProfileService profileService;
    private SeriesService seriesService;
    private SeriesProfileWatchlistService seriesProfileWatchlistService;

    public SeriesProfileWatchlistController(ProfileService profileService, SeriesService seriesService, SeriesProfileWatchlistService seriesProfileWatchlistService) {
        this.profileService = profileService;
        this.seriesService = seriesService;
        this.seriesProfileWatchlistService = seriesProfileWatchlistService;
    }

    @PostMapping("/{profileId}")
    public ResponseEntity<Object> addSeriesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer seriesId) {
        try {
            profileService.getProfileById(profileId);
            seriesService.getSeriesById(seriesId);
            seriesProfileWatchlistService.addSeriesProfileWatchlist(profileId, seriesId);
            return ResponseEntity.ok("Profile - series relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Object> getSeriesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer seriesId) {
        if (seriesProfileWatchlistService.getSeriesProfileWatchlist(profileId, seriesId) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Profile " + profileId + " - Series " + seriesId + " relation exists");
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Object> deleteSeriesProfileWatchlist(@PathVariable Integer profileId, @PathVariable Integer seriesId) {
        try {
            seriesProfileWatchlistService.deleteSeriesProfileWatchlist(profileId, seriesId);
            return ResponseEntity.ok("Profile - Series relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}