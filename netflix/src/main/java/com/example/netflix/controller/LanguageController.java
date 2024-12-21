package com.example.netflix.controller;

import com.example.netflix.entity.Language;
import com.example.netflix.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Integer id) {
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }

    @PostMapping
    public ResponseEntity<Language> addLanguage(@RequestBody Language language) {
        return ResponseEntity.ok(languageService.addLanguage(language));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(@PathVariable Integer id, @RequestBody Language updatedLanguage) {
        return ResponseEntity.ok(languageService.updateLanguage(id, updatedLanguage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLanguage(@PathVariable Integer id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.ok("Language deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Language> patchLanguage(@PathVariable Integer id, @RequestBody Language patchData) {
        return ResponseEntity.ok(languageService.patchLanguage(id, patchData));
    }
}
