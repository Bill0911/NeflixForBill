package com.example.netflix.service;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.User;
import com.example.netflix.repository.MovieViewCountRepository;
import com.example.netflix.repository.MovieRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieViewCountService {

    private final MovieViewCountRepository movieViewCountRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public MovieViewCountService(MovieViewCountRepository movieViewCountRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.movieViewCountRepository = movieViewCountRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public void incrementViewCount(Integer accountId, Integer movieId) {
        Optional<User> user = userRepository.findById(accountId);
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (user.isPresent() && movie.isPresent()) {
            System.out.println("The movie and the account exist with a" + accountId + ", m" + movieId);
            MovieViewCount movieViewCount = movieViewCountRepository.findByUser_AccountIdAndMovie_MovieId(accountId, movieId)
                    .orElse(new MovieViewCount());
            System.out.println("The movie / account exist");
            movieViewCount.setUser(user.get());
            movieViewCount.setMovie(movie.get());
            System.out.println("The movie id = " + movieViewCount.getMovie().getMovieId() + " and the account id = " + movieViewCount.getUser().getAccountId() + ", views: " + movieViewCount.getNumber());
            movieViewCountRepository.save(movieViewCount);
        }
    }
}
