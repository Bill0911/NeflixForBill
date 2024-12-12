package com.example.netflix.repository;

import com.example.netflix.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    @Query("SELECT e FROM Episode e WHERE e.series.seriesId = :seriesId AND e.episodeId > :currentEpisodeId ORDER BY e.episodeId ASC")
    Optional<Episode> findNextEpisode(@Param("seriesId") Integer seriesId, @Param("currentEpisodeId") Integer currentEpisodeId);

    Optional<Episode> findFirstBySeries_SeriesIdOrderByEpisodeIdAsc(Integer seriesId);
}
