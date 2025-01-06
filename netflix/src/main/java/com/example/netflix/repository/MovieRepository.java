package com.example.netflix.repository;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL AddMovie(:title, :duration, :sd_available, :hd_available, :uhd_available, :minimum_age)", nativeQuery = true)
    void addMovie(@Param("title") String title,
                  @Param("duration") LocalTime duration,
                  @Param("sd_available") Boolean sd_available,
                  @Param("hd_available") Boolean hd_available,
                  @Param("uhd_available") Boolean uhd_available,
                  @Param("minimum_age") Integer minimum_age);

    @Query(value = "CALL GetMovieById(:movieId)", nativeQuery = true)
    Optional<Movie> findByMovieId(@Param("movieId") Integer movieId);

    @Query(value = "CALL GetManyMovies()", nativeQuery = true)
    List<Movie> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteMovie(:movieId)", nativeQuery = true)
    void deleteByMovieId(@Param("movieId") Integer movieId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchMovie(:movieId, :title, :duration, :sd_available, :hd_available, :uhd_available, :minimum_age)", nativeQuery = true)
    void patchByMovieId(@Param("movieId") Integer movieId,
                        @Param("title") String title,
                        @Param("duration") LocalTime duration,
                        @Param("sd_available") Boolean sd_available,
                        @Param("hd_available") Boolean hd_available,
                        @Param("uhd_available") Boolean uhd_available,
                        @Param("minimum_age") Integer minimum_age);
    @Modifying
    @Transactional
    @Query(value = "CALL UpdateMovie(:movieId, :title, :duration, :sd_available, :hd_available, :uhd_available, :minimum_age)", nativeQuery = true)
    void updateByMovieId(@Param("movieId") Integer movieId,
                        @Param("title") String title,
                        @Param("duration") LocalTime duration,
                        @Param("sd_available") Boolean sd_available,
                        @Param("hd_available") Boolean hd_available,
                        @Param("uhd_available") Boolean uhd_available,
                        @Param("minimum_age") Integer minimum_age);
}
