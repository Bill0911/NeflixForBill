package com.example.netflix.controller;

import com.example.netflix.dto.InviteUserRequest;
import com.example.netflix.dto.MethodResponse;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.ResponseItem;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/profiles", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final MovieViewCountService movieViewCountService;
    private final InviteService inviteService;

    public ProfileController(UserService userService, ProfileService profileService, MovieViewCountService movieViewCountService, InviteService inviteService) {
        this.userService = userService;
        this.profileService = profileService;
        this.movieViewCountService = movieViewCountService;
        this.inviteService = inviteService;
    }

    @PostMapping()
    public ResponseEntity<Object> addProfile(@RequestBody Profile profile) {
        try {
            profileService.addProfile(profile);
            return ResponseEntity.ok(new ResponseItem("New movie inserted/added", HttpStatus.CREATED));
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> patchProfileById(@PathVariable Integer id, @RequestBody Profile profile) {
        try {
            profileService.patchProfileById(id, profile);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putProfileById(@PathVariable Integer id, @RequestBody Profile profile) {
        try {
            profileService.updateProfileById(id, profile);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("PATCH/PUT successful", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Item does not exist.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom patch/update error: Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to patch/update: " + e.getMessage());
        }
    }

    @PatchMapping("/movie-watching")
    public ResponseEntity<String> watchMovie(@RequestParam Integer profileId, @RequestParam Integer movieId, @RequestParam Integer accountId) {
        if (profileService.fitsForMovieWatching(profileId, movieId, accountId))
        {
            movieViewCountService.addMovieViewCount(accountId, movieId);
            return ResponseEntity.ok("Movie has been watched!");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Movie cannot be watched");
    }

    @GetMapping("/invites/by-inviter")
    public ResponseEntity<Object> getInvitesByInviter(@RequestParam String inviterEmail) {
        try {
            return ResponseEntity.ok(inviteService.getInvitesByInviterEmail(inviterEmail));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/invites")
    public ResponseEntity<Object> sendInvite(@RequestBody InviteUserRequest request) {
        try {
            inviteService.createInviteByEmail(request.getInviterEmail(), request.getInviteeEmail());
            return ResponseEntity.ok("Invite sent!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/invites/{invitationId}")
    public ResponseEntity<Object> updateInvite(
            @PathVariable Long invitationId,
            @RequestParam Long newInviteeId) {
        try {
            inviteService.updateInvitation(invitationId, newInviteeId);
            return ResponseEntity.ok("Invite updated!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/invites/{invitationId}")
    public ResponseEntity<Object> deleteInvite(@PathVariable Long invitationId) {
        try {
            inviteService.deleteInvitation(invitationId);
            return ResponseEntity.ok("Invite deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
