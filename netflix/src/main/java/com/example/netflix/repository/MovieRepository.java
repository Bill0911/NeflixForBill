package com.example.netflix.repository;

import com.example.netflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByGenreForMovies_Genre_GenreNameIn (List<String> genres);
        Movie findMovieByMovieId(Integer id);
}
