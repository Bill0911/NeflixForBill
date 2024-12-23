package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>
{
    @Query(value = "CALL AddGenre(:genreName)", nativeQuery = true)
    Integer addGenre(@Param("genreName") String genreName);
}
