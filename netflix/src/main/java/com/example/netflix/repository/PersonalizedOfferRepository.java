package com.example.netflix.repository;

import com.example.netflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalizedOfferRepository extends JpaRepository<Movie, Integer>
{
    @Query(value = "CALL GetPersonalizedOfferMovies(:userId, :maxMovies)", nativeQuery = true)
    List<Object[]> getPersonalizedOffer(@Param("userId") int userId, @Param("maxMovies") int maxMovies);
}
