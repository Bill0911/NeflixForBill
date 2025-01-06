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

    public void incrementViewCount(Integer accountId, Integer seriesId, Integer episodeId) {
        Optional<User> userOpt = userRepository.findById(accountId);
        Optional<Series> seriesOpt = seriesRepository.findById(seriesId);
        Optional<Episode> episodeOpt = episodeRepository.findById(episodeId);

        if (userOpt.isPresent() && seriesOpt.isPresent() && episodeOpt.isPresent()) {
            User user = userOpt.get();
            Series series = seriesOpt.get();
            Episode episode = episodeOpt.get();

            SeriesViewCount seriesViewCount = seriesViewCountRepository.findByUser_AccountIdAndSeries_SeriesIdAndEpisode_EpisodeId(accountId, seriesId, episodeId)
                    .orElse(new SeriesViewCount());

            seriesViewCount.setUser(user);
            seriesViewCount.setSeries(series);
            seriesViewCount.incrementViewCount();
            seriesViewCountRepository.save(seriesViewCount);

            // Logic to broadcast the next episode

            /*
            nextEpisodeOpt.ifPresent(nextEpisode -> {
                SeriesViewCount nextSeriesViewCount = new SeriesViewCount();
                nextSeriesViewCount.setUser(user);
                nextSeriesViewCount.setSeries(series);
                nextSeriesViewCount.setEpisode(nextEpisode);
                nextSeriesViewCount.setNumber(0);
                seriesViewCountRepository.save(nextSeriesViewCount);

            });

             */
        } else {
            if (userOpt.isEmpty()) {
                System.out.println("User not found with accountId: " + accountId);
            }
            if (seriesOpt.isEmpty()) {
                System.out.println("Series not found with seriesId: " + seriesId);
            }
            if (episodeOpt.isEmpty()) {
                System.out.println("Episode not found with episodeId: " + episodeId);
            }
        }
    }


/*
    public void addSeriesToViewCount(Integer accountId, Integer seriesId) {
        Optional<User> userOpt = userRepository.findById(accountId);
        Optional<Series> seriesOpt = seriesRepository.findById(seriesId);

        if (userOpt.isPresent() && seriesOpt.isPresent()) {
            User user = userOpt.get();
            Series series = seriesOpt.get();

            // Find the first episode of the series
            Optional<Episode> firstEpisodeOpt = episodeRepository.findFirstBySeries_SeriesIdOrderByEpisodeIdAsc(seriesId);
            if (firstEpisodeOpt.isPresent()) {
                Episode firstEpisode = firstEpisodeOpt.get();

                SeriesViewCount seriesViewCount = seriesViewCountRepository.findByUser_AccountIdAndSeries_SeriesIdAndEpisode_EpisodeId(accountId, seriesId, firstEpisode.getEpisodeId())
                        .orElse(new SeriesViewCount());

                seriesViewCount.setUser(user);
                seriesViewCount.setSeries(series);
                seriesViewCount.setEpisode(firstEpisode);
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

 */
}