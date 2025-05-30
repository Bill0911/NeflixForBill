package com.example.netflix.service;

import com.example.netflix.entity.MoviesProfileWatchlist;
import com.example.netflix.repository.MoviesProfileWatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesProfileWatchlistService {

    private final MoviesProfileWatchlistRepository moviesProfileWatchlistRepository;


    public MoviesProfileWatchlistService(MoviesProfileWatchlistRepository moviesProfileWatchlistRepository)
    {
        this.moviesProfileWatchlistRepository = moviesProfileWatchlistRepository;
    }

    public void addMoviesProfileWatchlist(Integer id1, Integer id2) {
        moviesProfileWatchlistRepository.add(id1, id2);
    }

    public MoviesProfileWatchlist getMoviesProfileWatchlist(Integer id1, Integer id2) {
        return moviesProfileWatchlistRepository.find(id1, id2).orElse(null);
    }

    public List<MoviesProfileWatchlist> getManyMoviesProfileWatchlists() {
        return moviesProfileWatchlistRepository.findMany();
    }

    public void deleteMoviesProfileWatchlist(Integer id1, Integer id2) {
        moviesProfileWatchlistRepository.delete(id1, id2);
    }

    public boolean existsByProfileIdAndMovieId(Integer profileId, Integer movieId) {
        return moviesProfileWatchlistRepository.existsByProfileIdAndMovieId(profileId, movieId);
    }

}
