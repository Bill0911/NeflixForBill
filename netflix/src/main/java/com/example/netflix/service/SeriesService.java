package com.example.netflix.service;

import com.example.netflix.entity.Series;
import com.example.netflix.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public Series addSeries(Series series) {
        seriesRepository.addSeries(series.getTitle(), series.getMinimumAge());
        return series;  // or fetch saved entity if needed
    }

    public Series getSeriesById(Integer id) {
        return seriesRepository.findBySeriesId(id).orElse(null);
    }

    public List<Series> getManySeries() {
        return seriesRepository.findMany();
    }

    public void deleteSeriesById(Integer id) {
        seriesRepository.deleteBySeriesId(id);
    }

    public Series patchSeriesById(Integer id, Series patchData) {
        seriesRepository.patchBySeriesId(id, patchData.getTitle(), patchData.getMinimumAge());
        return patchData;  // or fetch patched entity if needed
    }

    public Series updateSeriesById(Integer id, Series series) {
        seriesRepository.updateBySeriesId(id, series.getTitle(), series.getMinimumAge());
        return series;  // or fetch updated entity if needed
    }
}
