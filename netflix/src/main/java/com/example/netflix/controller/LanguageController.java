package com.example.netflix.controller;

import com.example.netflix.entity.Language;
import com.example.netflix.entity.Movie;
import com.example.netflix.service.LanguageService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Integer id) {
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }

    @GetMapping
    public ResponseEntity<Object> getAllLanguage() {
        return ResponseEntity.ok(languageService.getManyLanguages());
    }

    @PostMapping
    public ResponseEntity<Object> addLanguage(@RequestBody String name) {
        try {
            languageService.addLanguage(name);
            return ResponseEntity.ok("Language has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLanguageById(@PathVariable Integer id) {
        try {
            languageService.deleteLanguageById(id);
            return ResponseEntity.ok("Language deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchLanguageById(@PathVariable Integer id, @RequestBody Language patchLanguage) {
        try {
            languageService.patchLanguageById(id, patchLanguage);
            return ResponseEntity.ok("Language has been patched successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putLanguageById(@PathVariable Integer id, @RequestBody Language updatedLanguage) {
        try {
            languageService.updateLanguageById(id, updatedLanguage);
            return ResponseEntity.ok("Language has been updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
