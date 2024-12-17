package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.UserGenreCount;
import com.example.netflix.id.UserGenreCountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGenreCountRepository extends JpaRepository<UserGenreCount, UserGenreCountId> {
    List<UserGenreCount> findByUserId(Integer userId);
}
