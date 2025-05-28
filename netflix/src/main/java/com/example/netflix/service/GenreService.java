package com.example.netflix.service;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.dto.GenreViewCount;
import com.example.netflix.entity.Genre;
import com.example.netflix.entity.GenreWithoutMovieEntity;
import com.example.netflix.repository.GenreRepository;
import com.example.netflix.repository.GenreWithoutMovieRepository;
import com.example.netflix.repository.GenreViewCountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreWithoutMovieRepository genreWithoutMovieRepository;
    private final GenreViewCountRepository genreViewCountRepository;

    public GenreService(
            GenreRepository genreRepository,
            GenreWithoutMovieRepository genreWithoutMovieRepository,
            GenreViewCountRepository genreViewCountRepository) {
        this.genreRepository = genreRepository;
        this.genreWithoutMovieRepository = genreWithoutMovieRepository;
        this.genreViewCountRepository = genreViewCountRepository;
    }

    // Return DTOs for API use
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findMany().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<GenreDTO> getGenreById(Integer id) {
        return genreRepository.findByGenreId(id)
                .map(this::toDTO);
    }

    public void addGenre(String genreName) {
        genreRepository.addGenre(genreName);
    }

    public void updateGenre(Integer id, String genreName) {
        genreRepository.updateById(id, genreName);
    }

    // Not really needed with only one field, but included for completeness
    public GenreDTO patchGenre(Integer id, Genre patchData) {
        Genre genre = genreRepository.findById(id)
                .map(existingGenre -> {
                    if (patchData.getGenreName() != null) {
                        existingGenre.setGenreName(patchData.getGenreName());
                    }
                    return genreRepository.save(existingGenre);
                })
                .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + id));
        return toDTO(genre);
    }

    public void deleteGenre(Integer id) {
        genreRepository.deleteById(id);
    }

    public List<GenreDTO> getGenresWithoutMovie() {
        List<GenreWithoutMovieEntity> entities = genreWithoutMovieRepository.findAll();
        return entities.stream()
                .map(entity -> new GenreDTO(entity.getGenreId(), entity.getGenreName()))
                .collect(Collectors.toList());
    }

    public List<GenreViewCount> getViewCounts() {
        return genreViewCountRepository.findGenreViewCounts();
    }

    // Helper to map entity to DTO
    private GenreDTO toDTO(Genre genre) {
        return new GenreDTO(genre.getGenreId(), genre.getGenreName());
    }
}
