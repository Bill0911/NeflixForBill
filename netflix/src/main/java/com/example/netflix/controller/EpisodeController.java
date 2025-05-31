package com.example.netflix.controller;

import com.example.netflix.entity.Episode;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.EpisodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping()
    public ResponseEntity<Object> addEpisode(@RequestBody Episode episode) {
        try {
            episodeService.addEpisode(episode);
            return ResponseEntity.ok(new ResponseItem("New language inserted/added", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable Integer id) {
        return ResponseEntity.ok(episodeService.getEpisodeById(id));
    }

    @GetMapping()
    public ResponseEntity<Object> getManyEpisodes() {
        return ResponseEntity.ok(episodeService.getManyEpisodes());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteEpisodeById(@PathVariable Integer id) {
        try {
            episodeService.deleteEpisodeById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> patchEpisodeById(@PathVariable Integer id, @RequestBody Episode episode) {
        try {
            episodeService.patchEpisodeById(id, episode);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putEpisodeById(@PathVariable Integer id, @RequestBody Episode episode) {
        try {
            episodeService.updateEpisodeById(id, episode);
            return ResponseEntity.ok("Episode has been deleted successfully");
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }
}
