package com.example.netflix.controller;

import com.example.netflix.entity.Episode;
import com.example.netflix.entity.Profile;
import com.example.netflix.service.EpisodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/episodes", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping()
    public ResponseEntity<String> addEpisode(@RequestBody Episode episode) {
        try {
            episodeService.addEpisode(episode);
            return ResponseEntity.ok("Episode has been created");
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
            return ResponseEntity.ok("Episode has been deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> patchEpisodeById(@PathVariable Integer id, @RequestBody Episode episode) {
        try {
            episodeService.patchEpisodeById(id, episode);
            return ResponseEntity.ok("Episode title has been edited to " + episode.getTitle());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> putEpisodeById(@PathVariable Integer id, @RequestBody Episode episode) {
        try {
            episodeService.updateEpisodeById(id, episode);
            return ResponseEntity.ok("Episode has been deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
