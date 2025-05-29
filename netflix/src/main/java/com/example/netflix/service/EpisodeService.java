package com.example.netflix.service;

import com.example.netflix.entity.Episode;
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

    public void addEpisode(Episode episode) {
        episodeRepository.addEpisode(episode.getTitle(), episode.getDuration(), episode.getEpisodeId());
    }

    public Episode getEpisodeById(Integer id) {
        Optional<Episode> episode = episodeRepository.findByEpisodeId(id);
        return episode.orElse(null);
    }

    public List<Episode> getManyEpisodes() {
        return episodeRepository.findMany();
    }

    public void deleteEpisodeById(Integer id) {
        episodeRepository.deleteByEpisodeId(id);
    }

    public void patchEpisodeById(Integer id, Episode episode) {
        episodeRepository.patchByEpisodeId(id, episode.getTitle(), episode.getDuration(), episode.getEpisodeId());
    }

    public void updateEpisodeById(Integer id, Episode episode) {
        episodeRepository.updateByEpisodeId(id, episode.getTitle(), episode.getDuration(), episode.getEpisodeId());
    }
}
