package com.example.netflix.repository;

import com.example.netflix.entity.GenreWithoutMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreWithoutMovieRepository extends JpaRepository<GenreWithoutMovieEntity, Integer> {

}
