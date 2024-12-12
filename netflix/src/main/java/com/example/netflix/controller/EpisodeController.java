package com.example.netflix.controller;

import com.example.netflix.entity.Episode;
import com.example.netflix.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/series/episodes")
public class EpisodeController
{
    @Autowired
    private EpisodeService episodeService;

    @PostMapping
    public ResponseEntity<Episode> createEpisode(@RequestBody Episode episode)
    {
        Episode createdEpisode = episodeService.saveEpisode(episode);
        return ResponseEntity.ok(createdEpisode);
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable Integer episodeId) {
        Optional<Episode> episode = episodeService.getEpisodeById(episodeId);
        return episode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Episode>> getAllEpisodes() {
        List<Episode> episodes = episodeService.getAllEpisodes();
        return ResponseEntity.ok(episodes);
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Integer episodeId) {
        episodeService.deleteEpisode(episodeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/next")
    public ResponseEntity<Episode> getNextEpisode(@RequestParam Integer seriesId, @RequestParam Integer currentEpisodeId) {
        Optional<Episode> nextEpisode = episodeService.findNextEpisode(seriesId, currentEpisodeId);
        return nextEpisode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
