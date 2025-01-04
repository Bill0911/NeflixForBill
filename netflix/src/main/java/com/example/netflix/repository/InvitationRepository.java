package com.example.netflix.repository;

import com.example.netflix.entity.Invitation;
import com.example.netflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    boolean existsByInviterAndInvitee(User inviter, User invitee);
}
