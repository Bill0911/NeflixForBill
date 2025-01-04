package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "CALL GetUserByEmail(:email)", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "CALL GetUserById(:accountId)", nativeQuery = true)
    Optional<User> findByAccountId(@Param("accountId") Integer accountId);
}