package com.example.netflix.service;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.Profile;
import com.example.netflix.repository.MovieViewCountRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieViewCountService {

    private final MovieViewCountRepository movieViewCountRepository;


    public MovieViewCountService(MovieViewCountRepository movieViewCountRepository) {
        this.movieViewCountRepository = movieViewCountRepository;
    }

    public void addMovieViewCount(Integer accountId, Integer movieId) {
        movieViewCountRepository.add(accountId, movieId);
    }

    public MovieViewCount getMovieViewCount(Integer accountId, Integer movieId) {
        return movieViewCountRepository.find(accountId, movieId).orElse(null);
    }

    public List<MovieViewCount> getManyMovieViewCounts() {
        return movieViewCountRepository.findMany();
    }

    public void deleteMovieViewCount(Integer accountId, Integer movieId) {
        movieViewCountRepository.delete(accountId, movieId);
    }

    public void patchMovieViewCount(MovieViewCount movieViewCount) {
        System.out.println("CHECKPOINT - 3");
        movieViewCountRepository.patch(movieViewCount.getUser(), movieViewCount.getMovie(), movieViewCount.getNumber(), movieViewCount.getLastViewed());
        System.out.println("CHECKPOINT - 4");
    }

    public void updateMovieViewCount(MovieViewCount movieViewCount) {
        movieViewCountRepository.update(movieViewCount.getUser(), movieViewCount.getMovie(), movieViewCount.getNumber(), movieViewCount.getLastViewed());
    }
}
