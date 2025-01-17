package com.example.netflix.repository;


import com.example.netflix.dto.MovieDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MovieDTORepository extends JpaRepository<MovieDTO, String> {
    @Query(value = "SELECT * FROM movies_without_genre", nativeQuery = true)
    List<MovieDTO> findMoviesWithoutGenre();
}
