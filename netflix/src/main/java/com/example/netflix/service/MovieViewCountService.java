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
            movieViewCount.incrementViewCount();
            System.out.println("The movie id = " + movieViewCount.getMovie().getMovieId() + " and the account id = " + movieViewCount.getUser().getAccountId() + ", views: " + movieViewCount.getNumber());
            movieViewCountRepository.save(movieViewCount);
        }
    }

    public void addMovieToViewCount(Integer id, Integer movieId) {
        // Fetch the user from the username
        Optional<User> userOpt = userRepository.findByAccountId(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        // Fetch the movie
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        if (movieOpt.isEmpty()) {
            throw new RuntimeException("Movie not found");
        }

        Movie movie = movieOpt.get();

        // Check if the MovieViewCount entry already exists
        MovieViewCount movieViewCount = movieViewCountRepository.findByUser_AccountIdAndMovie_MovieId(user.getAccountId(), movie.getMovieId())
                .orElse(new MovieViewCount());

        movieViewCount.setUser(user);
        movieViewCount.setMovie(movie);
        movieViewCount.incrementViewCount(); // Increment view count
        movieViewCountRepository.save(movieViewCount);
    }
}
