package com.example.netflix.repository;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.MoviesProfileWatchlist;
import com.example.netflix.id.MoviesProfileWatchlistId;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MoviesProfileWatchlistRepository extends JpaRepository<MoviesProfileWatchlist, MoviesProfileWatchlistId> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddMoviesProfileWatchlist(:id1, :id2)", nativeQuery = true)
    void add(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetMoviesProfileWatchlist(:id1, :id2)", nativeQuery = true)
    Optional<MoviesProfileWatchlist> find(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetManyMoviesProfileWatchlists()", nativeQuery = true)
    List<MoviesProfileWatchlist> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteMoviesProfileWatchlist(:id1, :id2)", nativeQuery = true)
    void delete(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchMoviesProfileWatchlist(:id1, :id2,  :newId1, :newId2)", nativeQuery = true)
    void patch(@Param("id1") Integer id1, @Param("id2") Integer id2,
               @Param("newId1") Integer newId1, @Param("newId2") Integer newId2);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateMoviesProfileWatchlist(:id1, :id2, :newId1, :newId2)", nativeQuery = true)
    void update(@Param("id1") Integer id1, @Param("id2") Integer id2,
                @Param("newId1") Integer newId1, @Param("newId2") Integer newId2);
}
