package com.example.netflix.controller;

import com.example.netflix.dto.MethodResponse;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.MovieViewCountService;
import com.example.netflix.service.ProfileService;
import com.example.netflix.service.SeriesViewCountService;
import com.example.netflix.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
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

    @PostMapping("/watch-movie")
    public ResponseEntity<String> watchMovie(@RequestParam Integer profileId, @RequestParam Integer movieId, @RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        int id = jwtUtil.extractId(jwt);
        MethodResponse belongsToUser = profileService.belongsToUser(profileId, id);
        MethodResponse fitsMovieAgeRestrictions = profileService.fitsMovieAgeRestrictions(profileId, movieId);

        if (belongsToUser.isSuccess() && fitsMovieAgeRestrictions.isSuccess())
        {
            movieViewCountService.incrementMovieViewCount(id, movieId);
            return ResponseEntity.ok("Movie has been watched!");
        }

        return ResponseEntity.ok("Movie has not been watched: " + belongsToUser.getMessage() + " | " + fitsMovieAgeRestrictions.getMessage());
    }

    @PostMapping("/watch-series")
    public ResponseEntity<String> watchSeries(@RequestParam Integer profileId, @RequestParam Integer seriesId, @RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        int id = jwtUtil.extractId(jwt);
        MethodResponse belongsToUser = profileService.belongsToUser(profileId, id);
        MethodResponse fitsMovieAgeRestrictions = profileService.fitsMovieAgeRestrictions(profileId, seriesId);

        if (belongsToUser.isSuccess() && fitsMovieAgeRestrictions.isSuccess())
        {
            seriesViewCountService.addSeriesToViewCount(id, seriesId);
            return ResponseEntity.ok("Series has been watched!");
        }

        return ResponseEntity.ok("Series has not been watched: " + belongsToUser.getMessage() + " | " + fitsMovieAgeRestrictions.getMessage());
    }
}
