package com.example.netflix.repository;

import com.example.netflix.dto.GenreViewCount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface GenreViewCountRepository extends JpaRepository<GenreViewCount, String> {
    @Query(value = "SELECT * FROM genre_total_views", nativeQuery = true)
    List<GenreViewCount> findGenreViewCounts();
}
