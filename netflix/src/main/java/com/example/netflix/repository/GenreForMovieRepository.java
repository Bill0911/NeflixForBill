package com.example.netflix.repository;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.id.GenreForMovieId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreForMovieRepository extends JpaRepository<GenreForMovie, GenreForMovieId> {}
