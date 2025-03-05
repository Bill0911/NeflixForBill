package com.example.netflix.controller;

import com.example.netflix.entity.Invitation;
import com.example.netflix.repository.InvitationRepository;
import com.example.netflix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationRepository invitationRepository;
    private final UserService userService;

    @Autowired
    public InvitationController(InvitationRepository invitationRepository, UserService userService) {
        this.invitationRepository = invitationRepository;
        this.userService = userService;
    }

    // Endpoint to retrieve all invitations
    @GetMapping("/")
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        List<Invitation> invitations = invitationRepository.findMany();
        return ResponseEntity.ok(invitations);
    }
}
