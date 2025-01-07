package com.example.netflix.repository;

import com.example.netflix.entity.Language;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL addLanguage(:name)", nativeQuery = true)
    void addLanguage(@Param("name") String name);

    @Query(value = "CALL GetLanguageById(:languageId)", nativeQuery = true)
    Optional<Language> findByLanguageId(@Param("languageId") Integer languageId);

    @Query(value = "CALL GetManyLanguages()", nativeQuery = true)
    List<Language> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteLanguage(:languageId)", nativeQuery = true)
    void deleteLanguageById(@Param("languageId") Integer languageId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchLanguage(:languageId, :name)", nativeQuery = true)
    void patchByLanguageId(@Param("languageId") Integer languageId,
                           @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateLanguage(:languageId, :name)", nativeQuery = true)
    void updateLanguageById(@Param("languageId") Integer languageId,
                            @Param("name") String name);

}