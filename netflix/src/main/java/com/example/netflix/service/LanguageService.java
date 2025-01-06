package com.example.netflix.service;

import com.example.netflix.entity.Language;
import com.example.netflix.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public List<Language> getManyLanguages() {
        return languageRepository.findMany();
    }

    public Language getLanguageById(Integer id) {
        Optional<Language> language = languageRepository.findByLanguageId(id);
        return language.orElse(null);
    }

    public void addLanguage(String name) {
        languageRepository.addLanguage(name);
    }

    public void updateLanguageById(Integer languageId, Language patchData) {
        languageRepository.updateLanguageById(languageId, patchData.getName());
    }

    public void deleteLanguageById(Integer languageId) {
        languageRepository.deleteById(languageId);
    }

    public void patchLanguageById(Integer languageId, Language patchData) {
        languageRepository.patchByLanguageId(languageId, patchData.getName());
    }
}
