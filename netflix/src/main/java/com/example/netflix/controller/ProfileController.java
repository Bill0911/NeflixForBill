package com.example.netflix.controller;

import com.example.netflix.dto.MethodResponse;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.User;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.MovieViewCountService;
import com.example.netflix.service.ProfileService;
import com.example.netflix.service.SeriesViewCountService;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class    ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final MovieViewCountService movieViewCountService;
    private final SeriesViewCountService seriesViewCountService;
    private final JwtUtil jwtUtil;

    public ProfileController(UserService userService, ProfileService profileService, MovieViewCountService movieViewCountService, SeriesViewCountService seriesViewCountService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.profileService = profileService;
        this.movieViewCountService = movieViewCountService;
        this.seriesViewCountService = seriesViewCountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping()
    public ResponseEntity<String> addProfile(@RequestBody Profile profile) {
        try {
            profileService.addProfile(profile);
            return ResponseEntity.ok("Profile has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @GetMapping()
    public ResponseEntity<Object> getManyProfiles() {
        return ResponseEntity.ok(profileService.getManyProfiles());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteProfileById(@PathVariable Integer id) {
        try {
            profileService.deleteProfileById(id);
            return ResponseEntity.ok("Profile has been deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> patchProfileById(@PathVariable Integer id, @RequestBody Profile profile) {
        try {
            profileService.patchProfileById(id, profile);
            return ResponseEntity.ok("Profile has been patched successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> putProfileById(@PathVariable Integer id, @RequestBody Profile profile) {
        try {
            profileService.updateProfileById(id, profile);
            return ResponseEntity.ok("Profile has been deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/watch-movie")
    public ResponseEntity<String> watchMovie(@RequestParam Integer profileId, @RequestParam Integer movieId, @RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        int id = jwtUtil.extractId(jwt);
        MethodResponse fitsMovieAgeRestrictions = profileService.fitsMovieAgeRestrictions(profileId, movieId);

        if (fitsMovieAgeRestrictions.isSuccess())
        {
            movieViewCountService.addMovieViewCount(id, movieId);
            return ResponseEntity.ok("Movie has been watched!");
        }

        return ResponseEntity.ok("Movie has not been watched" + fitsMovieAgeRestrictions.getMessage());
    }

//    @PostMapping("/watch-series")
//    public ResponseEntity<String> watchSeries(@RequestParam Integer profileId, @RequestParam Integer seriesId, @RequestHeader("Authorization") String token) {
//        String jwt = token.substring(7);
//        int id = jwtUtil.extractId(jwt);
//        MethodResponse fitsMovieAgeRestrictions = profileService.fitsMovieAgeRestrictions(profileId, seriesId);
//
//        if (fitsMovieAgeRestrictions.isSuccess())
//        {
//            seriesViewCountService.addSeriesToViewCount(id, seriesId);
//            return ResponseEntity.ok("Series has been watched!");
//        }
//
//        return ResponseEntity.ok("Series has not been watched: " + fitsMovieAgeRestrictions.getMessage());
//    }


}
