package com.example.netflix.service;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.repository.GenreForSeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForSeriesService {

    private final GenreForSeriesRepository seriesProfileWatchlistRepository;


    public GenreForSeriesService(GenreForSeriesRepository seriesProfileWatchlistRepository)
    {
        this.seriesProfileWatchlistRepository = seriesProfileWatchlistRepository;
    }

    public void addGenreForSeries(Integer id1, Integer id2) {
        seriesProfileWatchlistRepository.add(id1, id2);
    }

    public GenreForSeries getGenreForSeries(Integer id1, Integer id2) {
        return seriesProfileWatchlistRepository.find(id1, id2).orElse(null);
    }

    public List<GenreForSeries> getManyGenreForSeriess() {
        return seriesProfileWatchlistRepository.findMany();
    }

    public void deleteGenreForSeries(Integer id1, Integer id2) {
        seriesProfileWatchlistRepository.delete(id1, id2);
    }

    public void patchGenreForSeries(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        System.out.println("CHECKPOINT - 3");
        seriesProfileWatchlistRepository.patch(id1, id2, newId1, newId2);
        System.out.println("CHECKPOINT - 4");
    }

    public void updateGenreForSeries(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        seriesProfileWatchlistRepository.update(id1, id2, newId1, newId2);
    }
}
