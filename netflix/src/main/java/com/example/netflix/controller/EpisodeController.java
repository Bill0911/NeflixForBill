package com.example.netflix.controller;

import com.example.netflix.dto.EpisodeDTO;
import com.example.netflix.dto.EpisodeListDTO;
import com.example.netflix.service.EpisodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/episodes",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addEpisode(@RequestBody EpisodeDTO episode) {
        episodeService.addEpisode(episode);
        return ResponseEntity.ok("Episode has been created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDTO> getEpisodeById(@PathVariable Integer id) {
        EpisodeDTO episode = episodeService.getEpisodeDTOById(id);
        return ResponseEntity.ok(episode);
    }

    @GetMapping()
    public ResponseEntity<EpisodeListDTO> getManyEpisodes() {
        return ResponseEntity.ok(new EpisodeListDTO(episodeService.getManyEpisodeDTOs()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEpisodeById(@PathVariable Integer id) {
        episodeService.deleteEpisodeById(id);
        return ResponseEntity.ok("Episode has been deleted successfully");
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> patchEpisodeById(@PathVariable Integer id, @RequestBody EpisodeDTO episode) {
        episodeService.patchEpisodeById(id, episode);
        return ResponseEntity.ok("Episode title has been edited to " + episode.getTitle());
    }

    @PutMapping("{id}")
    public ResponseEntity<String> putEpisodeById(@PathVariable Integer id, @RequestBody EpisodeDTO episode) {
        episodeService.updateEpisodeById(id, episode);
        return ResponseEntity.ok("Episode has been updated successfully");
    }
}
