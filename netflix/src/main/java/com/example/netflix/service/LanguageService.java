package com.example.netflix.service;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Language;
import com.example.netflix.repository.GenreRepository;
import com.example.netflix.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService
{
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllGenres() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Integer id) {
        Optional<Language> language = languageRepository.findById(id);
        return language.orElseThrow(() -> new RuntimeException("Language not found with ID: " + id));
    }

    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Integer id, Language languageDetails) {
        Language language = getLanguageById(id);
        language.setName(languageDetails.getName());
        language.setName(languageDetails.getName());
        return languageRepository.save(language);
    }

    public void deleteGenre(Integer id)
    {
        Language language = getLanguageById(id);
        languageRepository.delete(language);
    }
}
