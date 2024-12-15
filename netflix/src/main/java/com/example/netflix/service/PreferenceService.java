package com.example.netflix.service;

import com.example.netflix.dto.GenreCountDTO;
import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Movie;
import com.example.netflix.entity.User;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferenceService
{
    private UserRepository userRepository;
    private GenreForUserRepository genreForUserRepository;

    private final UserGenreCountRepository userGenreCountRepository;
    private GenreForSeriesRepository genreForSeriesRepository;
    private GenreForMovieRepository genreForMovieRepository;

    public PreferenceService(UserGenreCountRepository userGenreCountRepository) {
        this.userGenreCountRepository = userGenreCountRepository;
    }

    public User getMoviesByWatchedBefore(Integer accountId) {
        Optional<User> user = userRepository.findByAccountId(accountId);

        if (user.isPresent()) {

        }

        return null;
    }

    public List<GenreCountDTO> getGenreCountsByUser(Integer userId) {
        List<Object[]> results = userGenreCountRepository.findGenreCountsByUser(userId);
        return results.stream()
                .map(result -> new GenreCountDTO((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }
}
