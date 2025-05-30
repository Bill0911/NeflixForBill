package com.example.netflix.repository;

import com.example.netflix.entity.SeriesProfileWatchlist;
import com.example.netflix.id.SeriesProfileWatchlistId;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SeriesProfileWatchlistRepository extends JpaRepository<SeriesProfileWatchlist, SeriesProfileWatchlistId> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddSeriesProfileWatchlist(:id1, :id2)", nativeQuery = true)
    void add(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetSeriesProfileWatchlist(:id1, :id2)", nativeQuery = true)
    Optional<SeriesProfileWatchlist> find(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetManySeriesProfileWatchlists()", nativeQuery = true)
    List<SeriesProfileWatchlist> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteSeriesProfileWatchlist(:id1, :id2)", nativeQuery = true)
    void delete(@Param("id1") Integer id1, @Param("id2") Integer id2);

}
