package com.example.netflix.service;

import com.example.netflix.entity.SeriesProfileWatchlist;
import com.example.netflix.repository.SeriesProfileWatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesProfileWatchlistService {

    private final SeriesProfileWatchlistRepository moviesProfileWatchlistRepository;


    public SeriesProfileWatchlistService(SeriesProfileWatchlistRepository moviesProfileWatchlistRepository)
    {
        this.moviesProfileWatchlistRepository = moviesProfileWatchlistRepository;
    }

    public void addSeriesProfileWatchlist(Integer id1, Integer id2) {
        moviesProfileWatchlistRepository.add(id1, id2);
    }

    public SeriesProfileWatchlist getSeriesProfileWatchlist(Integer id1, Integer id2) {
        return moviesProfileWatchlistRepository.find(id1, id2).orElse(null);
    }

    public List<SeriesProfileWatchlist> getManySeriesProfileWatchlists() {
        return moviesProfileWatchlistRepository.findMany();
    }

    public void deleteSeriesProfileWatchlist(Integer id1, Integer id2) {
        moviesProfileWatchlistRepository.delete(id1, id2);
    }

    public void patchSeriesProfileWatchlist(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        System.out.println("CHECKPOINT - 3");
        moviesProfileWatchlistRepository.patch(id1, id2, newId1, newId2);
        System.out.println("CHECKPOINT - 4");
    }

    public void updateSeriesProfileWatchlist(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        moviesProfileWatchlistRepository.update(id1, id2, newId1, newId2);
    }
}
