package com.example.netflix.repository;

import com.example.netflix.entity.Episode;
import com.example.netflix.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddEpisode(:title, :duration, :seriesId)", nativeQuery = true)
    void addEpisode(@Param("title") String title,
                    @Param("duration") LocalTime duration,
                    @Param("seriesId") Integer seriesId);

    @Query(value = "CALL GetEpisodeById(:episodeId)", nativeQuery = true)
    Optional<Episode> findByEpisodeId(@Param("episodeId") Integer episodeId);

    @Query(value = "CALL GetManyEpisodes()", nativeQuery = true)
    List<Episode> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteEpisode(:episodeId)", nativeQuery = true)
    void deleteByEpisodeId(@Param("episodeId") Integer episodeId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchEpisode(:episodeId, :title, :duration, :seriesId)", nativeQuery = true)
    void patchByEpisodeId(@Param("episodeId") Integer episodeId,
                          @Param("title") String title,
                          @Param("duration") LocalTime duration,
                          @Param("seriesId") Integer seriesId);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateEpisode(:episodeId, :title, :duration, :seriesId)", nativeQuery = true)
    void updateByEpisodeId(@Param("episodeId") Integer episodeId,
                           @Param("title") String title,
                           @Param("duration") LocalTime duration,
                           @Param("seriesId") Integer seriesId);
}
