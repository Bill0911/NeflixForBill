package com.example.netflix.service;

import com.example.netflix.dto.EpisodeDTO;
import com.example.netflix.entity.Episode;
import com.example.netflix.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public void addEpisode(EpisodeDTO episodeDTO) {
        episodeRepository.addEpisode(
                episodeDTO.getTitle(),
                episodeDTO.getDuration(),
                episodeDTO.getSeries()
        );
    }

    public EpisodeDTO getEpisodeDTOById(Integer id) {
        Optional<Episode> episode = episodeRepository.findByEpisodeId(id);
        return episode.map(this::toDTO).orElse(null);
    }

    public List<EpisodeDTO> getManyEpisodeDTOs() {
        return episodeRepository.findMany()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteEpisodeById(Integer id) {
        episodeRepository.deleteByEpisodeId(id);
    }

    public void patchEpisodeById(Integer id, EpisodeDTO episodeDTO) {
        episodeRepository.patchByEpisodeId(
                id,
                episodeDTO.getTitle(),
                episodeDTO.getDuration(),
                episodeDTO.getSeries()
        );
    }

    public void updateEpisodeById(Integer id, EpisodeDTO episodeDTO) {
        episodeRepository.updateByEpisodeId(
                id,
                episodeDTO.getTitle(),
                episodeDTO.getDuration(),
                episodeDTO.getSeries()
        );
    }

    // Entity to DTO mapping
    private EpisodeDTO toDTO(Episode episode) {
        return new EpisodeDTO(
                episode.getEpisodeId(),
                episode.getTitle(),
                episode.getDuration(),
                episode.getSeries()
        );
    }
}
