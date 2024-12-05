package com.example.netflix.repository;


import com.example.netflix.entity.SeriesViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeriesViewCountRepository extends JpaRepository<SeriesViewCount, Integer> {
    Optional<SeriesViewCount> findByUser_AccountIdAndSeries_SeriesId(Integer accountId, Integer seriesId);
    //THis MF wasted me 3 hours because of naming convention
}
