package com.example.netflix.service;

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

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public Optional<Series> getSeriesById(Integer id) {
        return seriesRepository.findById(id);
    }

    public Series createSeries(Series series) {
        return seriesRepository.save(series);
    }

    public Series updateSeries(Integer id, Series updatedSeries) {
        return seriesRepository.findById(id)
                .map(existingSeries -> {
                    existingSeries.setTitle(updatedSeries.getTitle());
                    existingSeries.setMinimumAge(updatedSeries.getMinimumAge());
                    return seriesRepository.save(existingSeries);
                })
                .orElseThrow(() -> new RuntimeException("Series not found with ID: " + id));
    }

    public void deleteSeries(Integer id) {
        seriesRepository.deleteById(id);
    }

    public Series patchSeries(Integer id, Series partialSeries) {
        return seriesRepository.findById(id)
                .map(existingSeries -> {
                    if (partialSeries.getTitle() != null) {
                        existingSeries.setTitle(partialSeries.getTitle());
                    }
                    if (partialSeries.getMinimumAge() != null) {
                        existingSeries.setMinimumAge(partialSeries.getMinimumAge());
                    }
                    return seriesRepository.save(existingSeries);
                })
                .orElseThrow(() -> new RuntimeException("Series not found with ID: " + id));
    }
}
