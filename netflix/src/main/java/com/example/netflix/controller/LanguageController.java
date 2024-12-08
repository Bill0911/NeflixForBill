package com.example.netflix.controller;

import com.example.netflix.entity.Language;
import com.example.netflix.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguageController
{
    @Autowired
    private LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<Language>> getAllGenres() {
        return ResponseEntity.ok(languageService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getGenreById(@PathVariable Integer id) {
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }

    @PostMapping
    public ResponseEntity<Language> createGenre(@RequestBody Language language) {
        return ResponseEntity.ok(languageService.createLanguage(language));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Language> updateGenre(@PathVariable Integer id, @RequestBody Language language) {
        return ResponseEntity.ok(languageService.updateLanguage(id, language));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Integer id) {
        languageService.deleteGenre(id);
        return ResponseEntity.ok("Genre deleted successfully.");
    }
}
