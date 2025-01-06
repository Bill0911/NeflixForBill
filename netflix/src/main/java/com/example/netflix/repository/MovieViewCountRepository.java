package com.example.netflix.repository;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.Profile;
import com.example.netflix.id.MovieViewCountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovieViewCountRepository extends JpaRepository<MovieViewCount, MovieViewCountId> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddMovieViewCount(:accountId, :movieId)", nativeQuery = true)
    void add(@Param("accountId") Integer accountId, @Param("movieId") Integer movieId);

    @Query(value = "CALL GetMovieViewCount(:accountId, :movieId)", nativeQuery = true)
    Optional<MovieViewCount> find(@Param("accountId") Integer accountId, @Param("movieId") Integer movieId);

    @Query(value = "CALL GetManyMovieViewCounts()", nativeQuery = true)
    List<MovieViewCount> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteMovieViewCount(:accountId, :movieId)", nativeQuery = true)
    void delete(@Param("accountId") Integer accountId, @Param("movieId") Integer movieId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchMovieViewCount(:accountId, :movieId, :number, :lastViewed)", nativeQuery = true)
    void patch(@Param("accountId") Integer accountId,
               @Param("movieId") Integer movieId,
               @Param("number") Integer number,
               @Param("lastViewed") LocalDateTime lastViewed);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateMovieViewCount(:accountId, :movieId, :number, :lastViewed)", nativeQuery = true)
    void update(@Param("accountId") Integer accountId,
                @Param("movieId") Integer movieId,
                @Param("number") Integer number,
                @Param("lastViewed") LocalDateTime lastViewed);
}
