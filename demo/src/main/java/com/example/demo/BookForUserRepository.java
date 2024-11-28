package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookForUserRepository extends JpaRepository<BookForUser, Long> {
}
