package com.example.netflix.repository;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.id.GenreForSeriesId;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GenreForSeriesRepository extends JpaRepository<GenreForSeries, GenreForSeriesId> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddGenreForSeries(:id1, :id2)", nativeQuery = true)
    void add(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetGenreForSeries(:id1, :id2)", nativeQuery = true)
    Optional<GenreForSeries> find(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetManyGenreForSeries()", nativeQuery = true)
    List<GenreForSeries> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteGenreForSeries(:id1, :id2)", nativeQuery = true)
    void delete(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchGenreForSeries(:id1, :id2,  :newId1, :newId2)", nativeQuery = true)
    void patch(@Param("id1") Integer id1, @Param("id2") Integer id2,
               @Param("newId1") Integer newId1, @Param("newId2") Integer newId2);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateGenreForSeries(:id1, :id2, :newId1, :newId2)", nativeQuery = true)
    void update(@Param("id1") Integer id1, @Param("id2") Integer id2,
                @Param("newId1") Integer newId1, @Param("newId2") Integer newId2);
}
