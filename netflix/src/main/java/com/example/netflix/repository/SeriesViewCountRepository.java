package com.example.netflix.repository;


import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.SeriesViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesViewCountRepository extends JpaRepository<SeriesViewCount, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL AddSeriesViewCount(:accountId, :seriesId)", nativeQuery = true)
    void add(@Param("accountId") Integer accountId, @Param("movieId") Integer movieId);

    @Query(value = "CALL GetSeriesViewCount(:accountId, :seriesId)", nativeQuery = true)
    Optional<SeriesViewCount> find(@Param("accountId") Integer accountId, @Param("seriesId") Integer seriesId);

    @Query(value = "CALL GetManySeriesViewCounts()", nativeQuery = true)
    List<SeriesViewCount> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteSeriesViewCount(:accountId, :seriesId)", nativeQuery = true)
    void delete(@Param("accountId") Integer accountId, @Param("seriesId") Integer seriesId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchSeriesViewCount(:accountId, :seriesId, :number, :lastViewed)", nativeQuery = true)
    void patch(@Param("accountId") Integer accountId,
               @Param("seriesId") Integer seriesId,
               @Param("number") Integer number,
               @Param("lastViewed") LocalDateTime lastViewed);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateSeriesViewCount(:accountId, :seriesId, :number, :lastViewed)", nativeQuery = true)
    void update(@Param("accountId") Integer accountId,
                @Param("seriesId") Integer seriesId,
                @Param("number") Integer number,
                @Param("lastViewed") LocalDateTime lastViewed);
}
