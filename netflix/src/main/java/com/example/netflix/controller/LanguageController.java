package com.example.netflix.controller;

import com.example.netflix.entity.Language;
import com.example.netflix.entity.Movie;
import com.example.netflix.entity.ResponseItem;
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
            return ResponseEntity.status(HttpStatus.CREATED).body( new ResponseItem("New language inserted/added", HttpStatus.CREATED));
        } catch (Exception e) {
            if (e.getMessage().contains("command denied to user")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseItem("Custom error: this endpoint is not allowed for the user", HttpStatus.FORBIDDEN));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLanguageById(@PathVariable Integer id) {
        try {
            languageService.deleteLanguageById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("command denied to user")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseItem("Custom error: this endpoint is not allowed for the user", HttpStatus.FORBIDDEN));
            }
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchLanguageById(@PathVariable Integer id, @RequestBody Language patchLanguage) {
        try {
            languageService.patchLanguageById(id, patchLanguage);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putLanguageById(@PathVariable Integer id, @RequestBody Language updatedLanguage) {
        try {
            languageService.updateLanguageById(id, updatedLanguage);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }
}
