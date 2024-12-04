package com.example.netflix.repository;

import com.example.netflix.entity.Language;
import com.example.netflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<Language> findByLanguageId(Integer languageId);
}