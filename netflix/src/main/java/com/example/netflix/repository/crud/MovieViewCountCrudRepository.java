package com.example.netflix.repository.crud;

import com.example.netflix.entity.MovieViewCount;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface MovieViewCountCrudRepository extends CrudRepository<MovieViewCount, Integer> {
    @Procedure(name = "AddMovieViewCount")
    void addMovieViewCount(Integer movieId, Integer accountId);
}
