package com.example.netflix.service;

import com.example.netflix.dto.MovieInPersonalizedOffer;
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
    private final PersonalizedOfferRepository personalizedOfferRepository;
    private final MovieRepository movieRepository;
    private GenreForSeriesRepository genreForSeriesRepository;
    private GenreForMovieRepository genreForMovieRepository;

    public PersonalizedOfferService(UserGenreCountRepository userGenreCountRepository, PersonalizedOfferRepository personalizedOfferRepository, MovieRepository movieRepository) {
        this.userGenreCountRepository = userGenreCountRepository;
        this.personalizedOfferRepository = personalizedOfferRepository;
        this.movieRepository = movieRepository;
    }

    public List<UserGenreCount> getUserGenreCounts(Integer userId) {
        return userGenreCountRepository.findByUserId(userId);
    }

    public List<MovieInPersonalizedOffer> getPersonalizedOffer(int userId, int maxMovies) {
        // Call the repository method
        List<Object[]> results = personalizedOfferRepository.getPersonalizedOffer(userId, maxMovies);

        // Convert the results into Movie objects
        List<MovieInPersonalizedOffer> personalizedMovies = new ArrayList<>();
        for (Object[] result : results) {
            MovieInPersonalizedOffer movie = new MovieInPersonalizedOffer();
            movie.setMovieId((Integer) result[0]);  // Assuming column 0 is movie_id
            movie.setTitle((String) result[1]);    // Assuming column 1 is title
            personalizedMovies.add(movie);
        }

        return personalizedMovies;
    }
}
