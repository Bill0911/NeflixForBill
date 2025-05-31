package com.example.netflix.service;

import com.example.netflix.entity.Invitation;
import com.example.netflix.entity.User;
import com.example.netflix.repository.InviteRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    public InviteService(InviteRepository inviteRepository, UserRepository userRepository) {
        this.inviteRepository = inviteRepository;
        this.userRepository = userRepository;
    }

    // 1. Create an invitation using inviter and invitee emails
    public void createInviteByEmail(String inviterEmail, String inviteeEmail) {
        User inviter = userRepository.findByEmail(inviterEmail)
                .orElseThrow(() -> new RuntimeException("Inviter not found: " + inviterEmail));
        User invitee = userRepository.findByEmail(inviteeEmail)
                .orElseThrow(() -> new RuntimeException("Invitee not found: " + inviteeEmail));
        inviteRepository.addInvitation(inviter.getAccountId(), invitee.getAccountId());
    }

    // 2. List all invitations
    public List<Invitation> getAllInvitations() {
        return inviteRepository.getManyInvitations();
    }

    // 3. List invitations sent by a specific inviter (by email)
    public List<Invitation> getInvitesByInviterEmail(String inviterEmail) {
        User inviter = userRepository.findByEmail(inviterEmail)
                .orElseThrow(() -> new RuntimeException("Inviter not found: " + inviterEmail));
        Long inviterId = Long.valueOf(inviter.getAccountId());
        return inviteRepository.getManyInvitations().stream()
                .filter(invite -> invite.getInviterId().equals(inviterId))
                .toList();
    }

    // 4. Delete an invitation by its ID
    public void deleteInvitation(Long invitationId) {
        inviteRepository.deleteInvitation(invitationId);
    }

    // 5. Update the invitee for an invitation
    public void updateInvitation(Long invitationId, Long newInviteeId) {
        inviteRepository.updateInvitation(invitationId, newInviteeId);
    }
}
