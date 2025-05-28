package com.example.netflix.service;

import com.example.netflix.dto.GenreForMovieDTO;
import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.repository.GenreForMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreForMovieService {
    private final GenreForMovieRepository genreForMovieRepository;

    public GenreForMovieService(GenreForMovieRepository genreForMovieRepository) {
        this.genreForMovieRepository = genreForMovieRepository;
    }

    public void addGenreForMovie(Integer id1, Integer id2) {
        genreForMovieRepository.add(id1, id2);
    }

    public GenreForMovieDTO getGenreForMovie(Integer id1, Integer id2) {
        return genreForMovieRepository.find(id1, id2)
                .map(this::toDTO)
                .orElse(null);
    }

    public List<GenreForMovieDTO> getManyGenreForMovies() {
        return genreForMovieRepository.findMany()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteGenreForMovie(Integer id1, Integer id2) {
        genreForMovieRepository.delete(id1, id2);
    }

    public void patchGenreForMovie(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        genreForMovieRepository.patch(id1, id2, newId1, newId2);
    }

    public void updateGenreForMovie(Integer id1, Integer id2, Integer newId1, Integer newId2) {
        genreForMovieRepository.update(id1, id2, newId1, newId2);
    }

    private GenreForMovieDTO toDTO(GenreForMovie entity) {
        GenreForMovieDTO dto = new GenreForMovieDTO();
        dto.setGenreId(entity.getGenre());
        dto.setMovieId(entity.getMovie());
        return dto;
    }
}
