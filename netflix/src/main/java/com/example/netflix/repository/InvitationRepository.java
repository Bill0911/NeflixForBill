package com.example.netflix.repository;

import com.example.netflix.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    boolean existsByInviter_AccountIdAndInvitee_AccountId(Integer inviterAccountId, Integer inviteeAccountId);
}
