package com.example.netflix.service;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.entity.GenreWithoutMovieEntity;
import com.example.netflix.repository.GenreWithoutMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreWithoutMovieService {

    private final GenreWithoutMovieRepository genreWithoutMovieRepository;

    public GenreWithoutMovieService(GenreWithoutMovieRepository genreWithoutMovieRepository) {
        this.genreWithoutMovieRepository = genreWithoutMovieRepository;
    }

    public List<GenreDTO> getGenresWithoutMovie() {
        List<GenreWithoutMovieEntity> entities = genreWithoutMovieRepository.findAll();
        return entities.stream()
                .map(entity -> new GenreDTO(entity.getGenreId(), entity.getGenreName()))
                .collect(Collectors.toList());
    }
}
