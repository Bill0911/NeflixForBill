package com.example.netflix.repository;

import com.example.netflix.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invitation, Long> {

    @Modifying
    @Transactional
    @Query(value = "CALL AddInvitation(:inviterId, :inviteeId)", nativeQuery = true)
    void addInvitation(@Param("inviterId") Integer inviterId, @Param("inviteeId") Integer inviteeId);

    @Query(value = "CALL GetManyInvitations()", nativeQuery = true)
    List<Invitation> getManyInvitations();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteInvitation(:invitationId)", nativeQuery = true)
    void deleteInvitation(@Param("invitationId") Long invitationId);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateInvitation(:invitationId, :newInviteeId)", nativeQuery = true)
    void updateInvitation(@Param("invitationId") Long invitationId, @Param("newInviteeId") Long newInviteeId);

}

