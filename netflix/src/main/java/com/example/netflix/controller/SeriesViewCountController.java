package com.example.netflix.controller;

import com.example.netflix.entity.SeriesViewCount;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.SeriesService;
import com.example.netflix.service.SeriesViewCountService;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/series/{seriesId}/user", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class SeriesViewCountController {

    private final SeriesViewCountService seriesViewCountService;
    private final JwtUtil jwtUtil;

    private final SeriesService seriesService;

    private final UserService userService;

    public SeriesViewCountController(SeriesViewCountService seriesViewCountService, JwtUtil jwtUtil, SeriesService seriesService, UserService userService) {
        this.seriesViewCountService = seriesViewCountService;
        this.jwtUtil = jwtUtil;
        this.seriesService = seriesService;
        this.userService = userService;
    }
    
    @PostMapping("/{accountId}")
    public ResponseEntity<String> addSeriesViewCount(@PathVariable Integer accountId,  @PathVariable Integer seriesId) {
        try {
            userService.getUserById(accountId);
            seriesService.getSeriesById(seriesId);
            seriesViewCountService.addSeriesViewCount(accountId, seriesId);
            return ResponseEntity.ok("Series - User relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getSeriesViewCount(@PathVariable Integer accountId, @PathVariable Integer seriesId) {
        return ResponseEntity.ok(seriesViewCountService.getSeriesViewCount(accountId, seriesId));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Object> deleteSeriesViewCount(@PathVariable Integer accountId, @PathVariable Integer seriesId) {
        try {
            seriesViewCountService.deleteSeriesViewCount(accountId, seriesId);
            return ResponseEntity.ok("Series - User relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
