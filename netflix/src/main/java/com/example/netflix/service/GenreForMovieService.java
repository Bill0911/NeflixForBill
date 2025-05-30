package com.example.netflix.service;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.id.GenreForMovieId;
import com.example.netflix.repository.GenreForMovieRepository;
import com.example.netflix.repository.GenreForMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForMovieService {
    private final GenreForMovieRepository genreForMovieRepository;


    public GenreForMovieService(GenreForMovieRepository genreForMovieRepository)
    {
        this.genreForMovieRepository = genreForMovieRepository;
    }

    public void addGenreForMovie(Integer id1, Integer id2) {
        genreForMovieRepository.add(id1, id2);
    }

    public GenreForMovie getGenreForMovie(Integer id1, Integer id2) {
        return genreForMovieRepository.find(id1, id2).orElse(null);
    }

    public List<GenreForMovie> getManyGenreForMovies() {
        return genreForMovieRepository.findMany();
    }

    public void deleteGenreForMovie(Integer id1, Integer id2) {
        genreForMovieRepository.delete(id1, id2);
    }

}
