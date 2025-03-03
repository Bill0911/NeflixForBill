package com.example.netflix.repository;

import com.example.netflix.entity.Invitation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer>
{
    boolean existsByInviter_AccountIdAndInvitee_AccountId(Integer inviterAccountId, Integer inviteeAccountId);

    // Stored procedure calls for Invitation operations
    @Modifying
    @Transactional
    @Query(value = "CALL AddInvitation(:inviterId, :inviteeId)", nativeQuery = true)
    void addInvitation(@Param("inviterId") Integer inviterId, @Param("inviteeId") Integer inviteeId);

    @Query(value = "CALL GetManyInvitations()", nativeQuery = true)
    List<Invitation> findMany();  // Retrieve all invitations

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteInvitation(:invitationId)", nativeQuery = true)
    void deleteInvitationById(@Param("invitationId") Integer invitationId);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateInvitation(:invitationId, :inviteeId)", nativeQuery = true)
    void updateInvitationById(@Param("invitationId") Integer invitationId,
                              @Param("inviteeId") Integer newInviteeId);
}
