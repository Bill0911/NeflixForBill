package com.example.netflix.repository;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddSeries(:title, :minimum_age)", nativeQuery = true)
    void addSeries(@Param("title") String title,
                  @Param("minimum_age") Integer minimum_age);

    @Query(value = "CALL GetSeriesById(:seriesId)", nativeQuery = true)
    Optional<Series> findBySeriesId(@Param("seriesId") Integer movieId);

    @Query(value = "CALL GetManySeries()", nativeQuery = true)
    List<Series> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteSeries(:seriesId)", nativeQuery = true)
    void deleteBySeriesId(@Param("seriesId") Integer movieId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchSeries(:seriesId, :title, :minimum_age)", nativeQuery = true)
    void patchBySeriesId(@Param("seriesId") Integer movieId,
                        @Param("title") String title,
                        @Param("minimum_age") Integer minimum_age);
    @Modifying
    @Transactional
    @Query(value = "CALL UpdateSeries(:seriesId, :title, :minimum_age)", nativeQuery = true)
    void updateBySeriesId(@Param("seriesId") Integer movieId,
                         @Param("title") String title,
                         @Param("minimum_age") Integer minimum_age);
}
