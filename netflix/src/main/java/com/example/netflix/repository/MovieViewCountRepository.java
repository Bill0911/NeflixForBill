package com.example.netflix.repository;

import com.example.netflix.entity.MovieViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieViewCountRepository extends JpaRepository<MovieViewCount, Integer> {

    Optional<MovieViewCount> findByUser_AccountIdAndMovie_MovieId(Integer accountId, Integer movieId);
}