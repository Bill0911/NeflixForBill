package com.example.netflix.controller;

import com.example.netflix.entity.Episode;
import com.example.netflix.service.EpisodeService;
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

    @GetMapping
    public ResponseEntity<List<Episode>> getAllEpisodes() {
        return ResponseEntity.ok(episodeService.getAllEpisodes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable Integer id) {
        return episodeService.getEpisodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Episode> addEpisode(@RequestBody Episode episode) {
        return ResponseEntity.ok(episodeService.addEpisode(episode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Episode> updateEpisode(@PathVariable Integer id, @RequestBody Episode episode) {
        try {
            return ResponseEntity.ok(episodeService.updateEpisode(id, episode));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEpisode(@PathVariable Integer id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.ok("Episode deleted successfully.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Episode> patchEpisode(@PathVariable Integer id, @RequestBody Episode patchData) {
        try {
            return ResponseEntity.ok(episodeService.patchEpisode(id, patchData));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
