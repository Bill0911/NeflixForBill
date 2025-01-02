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

    public void incrementViewCount(Integer accountId, Integer seriesId) {
        Optional<User> userOpt = userRepository.findById(accountId);
        Optional<Series> seriesOpt = seriesRepository.findById(seriesId);

        if (userOpt.isPresent() && seriesOpt.isPresent()) {
            User user = userOpt.get();
            Series series = seriesOpt.get();

            SeriesViewCount seriesViewCount = seriesViewCountRepository.findByUser_AccountIdAndSeries_SeriesId(accountId, seriesId)
                    .orElse(new SeriesViewCount());

            seriesViewCount.setUser(user);
            seriesViewCount.setSeries(series);
            seriesViewCount.incrementViewCount();
            seriesViewCountRepository.save(seriesViewCount);

        } else {
            if (userOpt.isEmpty()) {
                System.out.println("User not found with accountId: " + accountId);
            }
            if (seriesOpt.isEmpty()) {
                System.out.println("Series not found with seriesId: " + seriesId);
            }
        }
    }

    public void addSeriesToViewCount(Integer accountId, Integer seriesId) {
        Optional<User> userOpt = userRepository.findById(accountId);
        Optional<Series> seriesOpt = seriesRepository.findById(seriesId);

        if (userOpt.isPresent() && seriesOpt.isPresent()) {
            User user = userOpt.get();
            Series series = seriesOpt.get();

            // Find the first episode of the series
            Optional<Episode> firstEpisodeOpt = episodeRepository.findFirstBySeries_SeriesIdOrderByEpisodeIdAsc(seriesId);
            if (firstEpisodeOpt.isPresent()) {

                SeriesViewCount seriesViewCount = seriesViewCountRepository.findByUser_AccountIdAndSeries_SeriesId(accountId, seriesId)
                        .orElse(new SeriesViewCount());

                seriesViewCount.setUser(user);
                seriesViewCount.setSeries(series);
                seriesViewCount.incrementViewCount();
                seriesViewCountRepository.save(seriesViewCount);
            } else {
                System.out.println("No episodes found for seriesId: " + seriesId);
            }
        } else {
            if (userOpt.isEmpty()) {
                System.out.println("User not found with accountId: " + accountId);
            }
            if (seriesOpt.isEmpty()) {
                System.out.println("Series not found with seriesId: " + seriesId);
            }
        }
    }
}