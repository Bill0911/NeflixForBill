package com.example.netflix.service;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Movie;
import com.example.netflix.entity.User;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.repository.GenreForMovieRepository;
import com.example.netflix.repository.GenreForSeriesRepository;
import com.example.netflix.repository.GenreForUserRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenceService
{
    private UserRepository userRepository;
    private GenreForUserRepository genreForUserRepository;
    private GenreForSeriesRepository genreForSeriesRepository;
    private GenreForMovieRepository genreForMovieRepository;

    public User getMoviesByWatchedBefore(Integer accountId) {
        Optional<User> user = userRepository.findByAccountId(accountId);
//        List<Optional<Genre>> = genreForUserRepository.findById() <---- findbyuserid

        if (user.isPresent()) {

        }

        return null;
    }

//    public List<Integer> getMovieWatchTimeProportions() {
//
//    }
//
//    public List<Integer> getSeriesWatchTimeProportions() {
//
//    }
}
