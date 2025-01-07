package com.example.netflix.repository;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.id.GenreForUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GenreForUserRepository extends JpaRepository<GenreForUser, GenreForUserId> {
    @Modifying
    @Transactional
    @Query(value = "CALL AddGenreForUser(:id1, :id2)", nativeQuery = true)
    void add(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetGenreForUser(:id1, :id2)", nativeQuery = true)
    Optional<GenreForUser> find(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Query(value = "CALL GetManyGenreForUsers()", nativeQuery = true)
    List<GenreForUser> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteGenreForUser(:id1, :id2)", nativeQuery = true)
    void delete(@Param("id1") Integer id1, @Param("id2") Integer id2);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchGenreForUser(:id1, :id2,  :newId1, :newId2)", nativeQuery = true)
    void patch(@Param("id1") Integer id1, @Param("id2") Integer id2,
               @Param("newId1") Integer newId1, @Param("newId2") Integer newId2);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateGenreForUser(:id1, :id2, :newId1, :newId2)", nativeQuery = true)
    void update(@Param("id1") Integer id1, @Param("id2") Integer id2,
                @Param("newId1") Integer newId1, @Param("newId2") Integer newId2);
}