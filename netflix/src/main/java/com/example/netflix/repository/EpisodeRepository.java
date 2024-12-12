package com.example.netflix.repository;

import com.example.netflix.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>
{
    Optional<Episode> findNextEpisode(Integer seriesId, Integer currentEpisodeId);
}
