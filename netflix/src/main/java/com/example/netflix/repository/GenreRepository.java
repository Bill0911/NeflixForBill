package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>
{
    @Modifying
    @Transactional
    @Query(value = "CALL AddGenre(:genreName)", nativeQuery = true)
    void addGenre(@Param("genreName") String genreName);

    @Query(value = "CALL GetGenreById(:genreId)", nativeQuery = true)
    Optional<Genre> findByGenreId(@Param("genreId") Integer genreId);

    @Query(value = "CALL GetAllGenres", nativeQuery = true)
    List<Genre> findAllGenres();
}
