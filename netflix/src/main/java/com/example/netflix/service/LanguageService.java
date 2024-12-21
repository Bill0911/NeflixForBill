package com.example.netflix.service;

import com.example.netflix.entity.Language;
import com.example.netflix.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Integer languageId) {
        return languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found with ID: " + languageId));
    }

    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Integer languageId, Language updatedLanguage) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found with ID: " + languageId));
        language.setName(updatedLanguage.getName());
        return languageRepository.save(language);
    }

    public void deleteLanguage(Integer languageId) {
        languageRepository.deleteById(languageId);
    }

    public Language patchLanguage(Integer languageId, Language patchData) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found with ID: " + languageId));

        if (patchData.getName() != null) {
            language.setName(patchData.getName());
        }

        return languageRepository.save(language);
    }
}
