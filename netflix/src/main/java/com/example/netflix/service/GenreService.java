package com.example.netflix.service;

import com.example.netflix.entity.Genre;
import com.example.netflix.repository.GenreRepository;
import com.example.netflix.repository.MovieRepository;
import com.example.netflix.repository.ProfileRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService
{
    @Autowired
    private GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository)
    {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres()
    {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Integer id)
    {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElseThrow(() -> new RuntimeException("Genre not found with ID: " + id));
    }

    public Genre createGenre(Genre genre)
    {
        return genreRepository.save(genre);
    }

}
