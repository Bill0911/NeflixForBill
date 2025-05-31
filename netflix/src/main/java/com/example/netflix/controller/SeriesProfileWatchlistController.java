package com.example.netflix.controller;

import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.SeriesService;
import com.example.netflix.service.SeriesProfileWatchlistService;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/series/{seriesId}/profile", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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
            seriesProfileWatchlistService.addSeriesProfileWatchlist(profileId, seriesId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseItem("Relation has been created", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseItem.errorCheckForRelationItemsPOST(e.getMessage());
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }
}