package com.example.netflix.service;

import com.example.netflix.entity.Episode;
import com.example.netflix.entity.Series;
import com.example.netflix.entity.SeriesViewCount;
import com.example.netflix.entity.User;
import com.example.netflix.repository.SeriesViewCountRepository;
import com.example.netflix.repository.SeriesRepository;
import com.example.netflix.repository.UserRepository;
import com.example.netflix.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesViewCountService {

    private final SeriesViewCountRepository seriesViewCountRepository;
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    public SeriesViewCountService(SeriesViewCountRepository seriesViewCountRepository, SeriesRepository seriesRepository, UserRepository userRepository, EpisodeRepository episodeRepository) {
        this.seriesViewCountRepository = seriesViewCountRepository;
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
        this.episodeRepository = episodeRepository;
    }

    public void addSeriesViewCount(Integer accountId, Integer seriesId) {
        seriesViewCountRepository.addSeriesViewCount(accountId, seriesId);
    }

    public SeriesViewCount getSeriesViewCount(Integer accountId, Integer seriesId) {
        return seriesViewCountRepository.find(accountId, seriesId).orElse(null);
    }

    public List<SeriesViewCount> getManySeriesViewCounts() {
        return seriesViewCountRepository.findMany();
    }

    public void deleteSeriesViewCount(Integer accountId, Integer seriesId) {
        seriesViewCountRepository.delete(accountId, seriesId);
    }
}