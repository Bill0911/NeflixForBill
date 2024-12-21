package com.example.netflix.repository;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.id.MovieViewCountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieViewCountRepository extends JpaRepository<MovieViewCount, MovieViewCountId> {
    Optional<MovieViewCount> findByUserAndMovie(Integer user, Integer movie);
}
