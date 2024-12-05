package com.example.netflix.repository;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.SeriesViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesViewCountRepository extends JpaRepository<SeriesViewCount, Integer> {
    Optional<SeriesViewCount> findByUser_AccountID_And_SeriesID(Integer accountId, Integer SeriesId);
}
