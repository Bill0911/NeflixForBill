package com.example.netflix.service;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Series;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.repository.GenreForSeriesRepository;
import com.example.netflix.repository.GenreRepository;
import com.example.netflix.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForSeriesService {

    private final GenreForSeriesRepository genreForSeriesRepository;
    private final SeriesRepository seriesRepository;
    private final GenreRepository genreRepository;

    public GenreForSeriesService(GenreForSeriesRepository genreForSeriesRepository, SeriesRepository seriesRepository, GenreRepository genreRepository) {
        this.genreForSeriesRepository = genreForSeriesRepository;
        this.seriesRepository = seriesRepository;
        this.genreRepository = genreRepository;
    }

    public List<GenreForSeries> getAll() {
        return genreForSeriesRepository.findAll();
    }

    public GenreForSeries addGenreForSeries(Integer seriesId, Integer genreId) {
        Series series = seriesRepository.findById(seriesId).orElseThrow(() -> new RuntimeException("Series not found"));
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));

        GenreForSeries genreForSeries = new GenreForSeries();
        genreForSeries.setSeries(series);
        genreForSeries.setGenre(genre);

        return genreForSeriesRepository.save(genreForSeries);
    }

    public void deleteGenreForSeries(Integer seriesId, Integer genreId) {
        genreForSeriesRepository.deleteById(new GenreForSeriesId(seriesId, genreId));
    }

    public GenreForSeries patchGenreForSeries(Integer seriesId, Integer oldGenreId, Integer newGenreId) {
        GenreForSeriesId id = new GenreForSeriesId(seriesId, oldGenreId);
        GenreForSeries genreForSeries = genreForSeriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GenreForSeries relationship not found"));

        Genre newGenre = genreRepository.findById(newGenreId).orElseThrow(() -> new RuntimeException("New Genre not found"));

        genreForSeries.setGenre(newGenre);
        return genreForSeriesRepository.save(genreForSeries);
    }
}
