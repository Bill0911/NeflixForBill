package com.example.netflix.service.crud;

import com.example.netflix.repository.crud.SeriesViewCountCrudRepository;

public class SeriesViewCountCrudService
{
    private final SeriesViewCountCrudRepository seriesViewCountCrudRepository;

    public SeriesViewCountCrudService(SeriesViewCountCrudRepository seriesViewCountCrudRepository)
    {
        this.seriesViewCountCrudRepository = seriesViewCountCrudRepository;
    }

    public void incrementSeriesViewCount(Integer seriesId, Integer accountId, Integer episodeId)
    {
        seriesViewCountCrudRepository.addSeriesViewCount(seriesId, accountId, episodeId);
    }
}
