package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    boolean existsByInviter_AccountIdAndInvitee_AccountId(Integer inviterAccountId, Integer inviteeAccountId);


    @Modifying
    @Transactional
    @Query(value = "CALL AddInvitation(:invitationName)", nativeQuery = true)
    void addGenre(@Param("genreName") String genreName);

    @Query(value = "CALL GetManyInvitation", nativeQuery = true)
    List<Genre> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteInvitation(:invitationId)", nativeQuery = true)
    void deleteById(@Param("invitationId") Integer invitationId);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateInvitation(:invitationId, :invitationName)", nativeQuery = true)
    void updateById(@Param("genreId") Integer genreId, @Param("invitationName") String genreName);
}
