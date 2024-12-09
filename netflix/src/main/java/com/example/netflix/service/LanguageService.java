package com.example.netflix.service;

import com.example.netflix.entity.Language;
import com.example.netflix.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService
{

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository)
    {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages()
    {
        return languageRepository.findAll();
    }

    public Optional<Language> getLanguageById(Integer id)
    {
        return languageRepository.findById(id);
    }

    public Language createLanguage(Language language)
    {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Integer id, Language updatedLanguage)
    {
        return languageRepository.findById(id)
                .map(existingLanguage -> {
                    existingLanguage.setName(updatedLanguage.getName());
                    return languageRepository.save(existingLanguage);
                })
                .orElseThrow(() -> new RuntimeException("Language not found with id: " + id));
    }

    public void deleteLanguage(Integer id)
    {
        languageRepository.deleteById(id);
    }
}
