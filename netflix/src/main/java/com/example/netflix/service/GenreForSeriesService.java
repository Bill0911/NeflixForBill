package com.example.netflix.service;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.repository.GenreForSeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForSeriesService {

    private final GenreForSeriesRepository genreForSeriesRepository;


    public GenreForSeriesService(GenreForSeriesRepository genreForSeriesRepository)
    {
        this.genreForSeriesRepository = genreForSeriesRepository;
    }

    public void addGenreForSeries(Integer id1, Integer id2) {
        genreForSeriesRepository.add(id1, id2);
    }

    public GenreForSeries getGenreForSeries(Integer id1, Integer id2) {
        return genreForSeriesRepository.find(id1, id2).orElse(null);
    }

    public List<GenreForSeries> getManyGenreForSeries() {
        return genreForSeriesRepository.findMany();
    }

    public void deleteGenreForSeries(Integer id1, Integer id2) {
        genreForSeriesRepository.delete(id1, id2);
    }

    public void patchGenreForSeries(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        System.out.println("CHECKPOINT - 3");
        genreForSeriesRepository.patch(id1, id2, newId1, newId2);
        System.out.println("CHECKPOINT - 4");
    }

    public void updateGenreForSeries(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        genreForSeriesRepository.update(id1, id2, newId1, newId2);
    }
}
