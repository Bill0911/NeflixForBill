package com.example.netflix.repository;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.id.GenreForUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreForUserRepository extends JpaRepository<GenreForUser, GenreForUserId> {
}
