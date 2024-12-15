package com.example.netflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserGenreCountRepository extends JpaRepository<Object, Long> {

    @Query(value = "SELECT genre_name, total_views FROM user_genre_count WHERE user_id = :userId", nativeQuery = true)
    List<Object[]> findGenreCountsByUser(@Param("userId") Integer userId);
}
