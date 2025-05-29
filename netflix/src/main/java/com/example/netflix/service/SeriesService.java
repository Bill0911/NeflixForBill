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

    public void addSeries(Series series) {
        seriesRepository.addSeries(series.getTitle(), series.getMinimumAge());
    }

    public Series getSeriesById(Integer id) {
        Optional<Series> series = seriesRepository.findBySeriesId(id);
        return series.orElse(null);
    }

    public List<Series> getManySeries() {
        return seriesRepository.findMany();
    }

    public void deleteSeriesById(Integer id) {
        seriesRepository.deleteBySeriesId(id);
    }

    public void patchSeriesById(Integer id, Series patchData) {
        seriesRepository.patchBySeriesId(id, patchData.getTitle(), patchData.getMinimumAge());
    }

    public void updateSeriesById(Integer id, Series series) {
        seriesRepository.updateBySeriesId(id, series.getTitle(), series.getMinimumAge());
    }
}
