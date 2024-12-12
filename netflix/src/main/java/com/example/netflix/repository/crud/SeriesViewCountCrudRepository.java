package com.example.netflix.repository.crud;

import com.example.netflix.entity.SeriesViewCount;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface SeriesViewCountCrudRepository extends CrudRepository<SeriesViewCount, Integer>
{
    @Procedure(name = "AddSeriesViewCount")
    void addSeriesViewCount(Integer seriesId, Integer accountId, Integer episodeId);
}
