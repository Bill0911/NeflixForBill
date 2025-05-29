package com.example.netflix.repository;

import com.example.netflix.dto.GenreDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface GenreDTORepository extends JpaRepository<GenreDTO, String> {
    @Query(value = "SELECT * FROM genres_without_movie", nativeQuery = true)
    List<GenreDTO> findGenresWithoutMovie();
}
