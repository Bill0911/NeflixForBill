package com.example.netflix.service;

import com.example.netflix.entity.SeriesProfileWatchlist;
import com.example.netflix.repository.SeriesProfileWatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesProfileWatchlistService {

    private final SeriesProfileWatchlistRepository seriesProfileWatchlistRepository;


    public SeriesProfileWatchlistService(SeriesProfileWatchlistRepository seriesProfileWatchlistRepository)
    {
        this.seriesProfileWatchlistRepository = seriesProfileWatchlistRepository;
    }

    public void addSeriesProfileWatchlist(Integer id1, Integer id2) {
        seriesProfileWatchlistRepository.add(id1, id2);
    }

    public SeriesProfileWatchlist getSeriesProfileWatchlist(Integer id1, Integer id2) {
        return seriesProfileWatchlistRepository.find(id1, id2).orElse(null);
    }

    public List<SeriesProfileWatchlist> getManySeriesProfileWatchlists() {
        return seriesProfileWatchlistRepository.findMany();
    }

    public void deleteSeriesProfileWatchlist(Integer id1, Integer id2) {
        seriesProfileWatchlistRepository.delete(id1, id2);
    }

    public void patchSeriesProfileWatchlist(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        System.out.println("CHECKPOINT - 3");
        seriesProfileWatchlistRepository.patch(id1, id2, newId1, newId2);
        System.out.println("CHECKPOINT - 4");
    }

    public void updateSeriesProfileWatchlist(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        seriesProfileWatchlistRepository.update(id1, id2, newId1, newId2);
    }
}
