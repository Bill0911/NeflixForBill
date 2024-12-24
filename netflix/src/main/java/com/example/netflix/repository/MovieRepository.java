package com.example.netflix.repository;

import com.example.netflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findMovieByMovieId(Integer movieId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchMovie(:movieId, :title, :duration, :sd_available, " +
            ":hd_available, :uhd_available, :minimum_age)", nativeQuery = true)
    void patchById(@Param("movieId") Integer movieId,
                   @Param("title") String title,
                   @Param("duration") LocalTime duration,
                   @Param("sd_available") boolean sd_available,
                   @Param("hd_available") boolean hd_available,
                   @Param("uhd_available") boolean uhd_available,
                   @Param("minimum_age") Integer minimum_age);
}
