package com.example.netflix.service;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.UserGenreCount;
import com.example.netflix.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PersonalizedOfferService
{
    private UserRepository userRepository;
    private GenreForUserRepository genreForUserRepository;

    private final UserGenreCountRepository userGenreCountRepository;
    private final MovieRepository movieRepository;
    private GenreForSeriesRepository genreForSeriesRepository;
    private GenreForMovieRepository genreForMovieRepository;

    public PersonalizedOfferService(UserGenreCountRepository userGenreCountRepository, MovieRepository movieRepository) {
        this.userGenreCountRepository = userGenreCountRepository;
        this.movieRepository = movieRepository;
    }

    public List<UserGenreCount> getUserGenreCounts(Integer userId) {
        return userGenreCountRepository.findByUserId(userId);
    }

    public List<Movie> getPersonalizedMovies(Integer userId) {
        // Step 1: Fetch user's genre preferences
        List<UserGenreCount> genreCounts = userGenreCountRepository.findByUserId(userId);

        // Step 2: Build a weighted list of movies
        List<Movie> weightedMovies = new ArrayList<>();
        for (UserGenreCount genreCount : genreCounts) {
            List<Movie> moviesByGenre = movieRepository.findByGenreForMovies_Genre_GenreId(genreCount.getGenreId());

            // Add movies to the weighted list based on total_views
            for (int i = 0; i < genreCount.getTotalViews(); i++) {
                weightedMovies.addAll(moviesByGenre);
            }
        }

        // Step 3: Randomize the result
        Collections.shuffle(weightedMovies);

        // Step 4: Limit the number of results (e.g., top 20 movies)
        return weightedMovies.stream().distinct().limit(20).toList();
    }
}
