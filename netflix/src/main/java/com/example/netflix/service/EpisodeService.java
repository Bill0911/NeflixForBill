package com.example.netflix.service;

import com.example.netflix.entity.Episode;
import com.example.netflix.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    public Optional<Episode> getEpisodeById(Integer id) {
        return episodeRepository.findById(id);
    }

    public Episode addEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    public Episode updateEpisode(Integer id, Episode updatedEpisode) {
        return episodeRepository.findById(id)
                .map(existingEpisode -> {
                    existingEpisode.setTitle(updatedEpisode.getTitle());
                    existingEpisode.setDuration(updatedEpisode.getDuration());
                    existingEpisode.setSeries(updatedEpisode.getSeries());
                    return episodeRepository.save(existingEpisode);
                })
                .orElseThrow(() -> new RuntimeException("Episode not found with ID: " + id));
    }

    public void deleteEpisode(Integer id) {
        episodeRepository.deleteById(id);
    }

    public Episode patchEpisode(Integer id, Episode patchData) {
        return episodeRepository.findById(id)
                .map(existingEpisode -> {
                    if (patchData.getTitle() != null) {
                        existingEpisode.setTitle(patchData.getTitle());
                    }
                    if (patchData.getDuration() != null) {
                        existingEpisode.setDuration(patchData.getDuration());
                    }
                    if (patchData.getSeries() != null) {
                        existingEpisode.setSeries(patchData.getSeries());
                    }
                    return episodeRepository.save(existingEpisode);
                })
                .orElseThrow(() -> new RuntimeException("Episode not found with ID: " + id));
    }
}
