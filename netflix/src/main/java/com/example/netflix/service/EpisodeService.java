package com.example.netflix.service;

import com.example.netflix.entity.Episode;
import com.example.netflix.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService
{
    @Autowired
    private EpisodeRepository episodeRepository;

    public Episode saveEpisode(Episode episode)
    {
        return this.episodeRepository.save(episode);
    }

    public Optional<Episode> getEpisodeById(Integer episodeId)
    {
        return this.episodeRepository.findById(episodeId);
    }

    public List<Episode> getAllEpisodes()
    {
        return this.episodeRepository.findAll();
    }

    public void deleteEpisode(Integer episodeId)
    {
        this.episodeRepository.deleteById(episodeId);
    }

    public Optional<Episode> findNextEpisode(Integer seriesId, Integer currentEpisodeId)
    {
        return this.episodeRepository.findNextEpisode(seriesId, currentEpisodeId);
    }
}
