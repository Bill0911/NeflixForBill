package com.example.netflix.repository;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.id.GenreForMovieId;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GenreForMovieRepository extends JpaRepository<GenreForMovie, GenreForMovieId> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddGenreForMovie(:id1, :id2)", nativeQuery = true)
    void add(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetGenreForMovie(:id1, :id2)", nativeQuery = true)
    Optional<GenreForMovie> find(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetManyGenreForMovies()", nativeQuery = true)
    List<GenreForMovie> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteGenreForMovie(:id1, :id2)", nativeQuery = true)
    void delete(@Param("id1") Integer id1, @Param("id2") Integer id2);
}