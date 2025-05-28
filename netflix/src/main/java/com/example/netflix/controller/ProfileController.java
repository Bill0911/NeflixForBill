package com.example.netflix.controller;

import com.example.netflix.entity.Profile;
import com.example.netflix.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/profiles",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<String> addProfile(@RequestBody Profile profile) {
        profileService.addProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body("Profile created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Integer id) {
        Profile profile = profileService.getProfileById(id);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getManyProfiles() {
        return ResponseEntity.ok(profileService.getManyProfiles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfileById(@PathVariable Integer id) {
        profileService.deleteProfileById(id);
        return ResponseEntity.ok("Profile deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchProfileById(@PathVariable Integer id, @RequestBody Profile profile) {
        profileService.patchProfileById(id, profile);
        return ResponseEntity.ok("Profile patched successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putProfileById(@PathVariable Integer id, @RequestBody Profile profile) {
        profileService.updateProfileById(id, profile);
        return ResponseEntity.ok("Profile updated successfully");
    }
}
