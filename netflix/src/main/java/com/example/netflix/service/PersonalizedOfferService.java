package com.example.netflix.service;

import com.example.netflix.dto.PersonalizedOfferItem;
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

    public List<PersonalizedOfferItem> getPersonalizedOfferMovies(int userId, int maxMovies) {
        System.out.println("The results are NOT YET retrieved (doubled message)");

        List<Object[]> results = personalizedOfferRepository.getPersonalizedOffer(userId, maxMovies);

        System.out.println("The results are retrieved");

        List<PersonalizedOfferItem> personalizedMovies = new ArrayList<>();
        for (Object[] result : results) {
            PersonalizedOfferItem movie = new PersonalizedOfferItem();
            System.out.println(result[0] + " <-> " + result[1]);
            movie.setId((Integer) result[0]);
            movie.setTitle((String) result[1]);
            personalizedMovies.add(movie);
        }

        return personalizedMovies;
    }
}
