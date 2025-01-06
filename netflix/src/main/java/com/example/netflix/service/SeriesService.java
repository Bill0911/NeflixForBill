package com.example.netflix.service;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Series;
import com.example.netflix.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public void addMovie(Series series) {
        seriesRepository.addSeries(series.getTitle(), series.getMinimumAge());
    }

    public Movie getMovieById(Integer id) {
        Optional<Movie> movie = seriesRepository.findBySeriesId(id);
        return movie.orElse(null);
    }

    public List<Movie> getManyMovies() {
        return seriesRepository.findMany();
    }

    public void deleteMovieById(Integer id) {
        seriesRepository.deleteBySeriesId(id);
    }

    public void patchMovieById(Integer id, Series patchData) {
        seriesRepository.patchBySeriesId(id, patchData.getTitle(), patchData.getMinimumAge());
    }

    public void updateMovieById(Integer id, Series patchData) {
        seriesRepository.updateBySeriesId(id, patchData.getTitle(), patchData.getMinimumAge());
    }
}
