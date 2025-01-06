package com.example.netflix.service;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.entity.Genre;
import com.example.netflix.repository.GenreRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findMany();
    }

    public Optional<Genre> getGenreById(Integer id) {
        return genreRepository.findByGenreId(id);
    }

    public void addGenre(String genreName) {
        System.out.println("Genre has not been added yet");
        genreRepository.addGenre(genreName);
        System.out.println("Genre has been added ");
    }

    public void updateGenre(Integer id, String genreName) {
        genreRepository.updateById(id, genreName);;
    }

    public Genre patchGenre(Integer id, Genre patchData) {
        return genreRepository.findById(id)
                .map(existingGenre -> {
                    if (patchData.getGenreName() != null) {
                        existingGenre.setGenreName(patchData.getGenreName());
                    }
                    return genreRepository.save(existingGenre);
                })
                .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + id));
    }

    public void deleteGenre(Integer id) {
        genreRepository.deleteById(id);
    }
}
