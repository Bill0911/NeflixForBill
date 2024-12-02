package com.example.netflix.repository;

import com.example.netflix.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    // Custom query methods (if needed) can be added here
}