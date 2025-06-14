package com.example.netflix.controller;

import com.example.netflix.entity.ResponseItem;
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
    public ResponseEntity<Object> addSeriesViewCount(@PathVariable Integer accountId,  @PathVariable Integer seriesId) {
        try {
            seriesViewCountService.addSeriesViewCount(accountId, seriesId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseItem("Relation has been created", HttpStatus.CREATED));
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }
}
