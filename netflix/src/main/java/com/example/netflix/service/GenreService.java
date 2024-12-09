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
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository)
    {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll()
    {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Integer id)
    {
        return genreRepository.findById(id);
    }

    public Genre save(Genre genre)
    {
        return genreRepository.save(genre);
    }

    public Genre update(Integer id, Genre genre)
    {
        if (genreRepository.existsById(id))
        {
            genre.setGenreId(id);
            return genreRepository.save(genre);
        }
        throw new IllegalArgumentException("Genre with ID " + id + " not found.");
    }

    public void deleteById(Integer id)
    {
        genreRepository.deleteById(id);
    }
}
