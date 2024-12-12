package com.example.netflix.repository;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.id.GenreForSeriesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreForSeriesRepository extends JpaRepository<GenreForSeries, GenreForSeriesId> {
}
