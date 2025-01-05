package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Role;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Flow;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "CALL GetUserByEmail(:email)", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "CALL GetUserById(:accountId)", nativeQuery = true)
    Optional<User> findByAccountId(@Param("accountId") Integer accountId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchUser(:accountId)", nativeQuery = true)
    void patchByAccountId(@Param("accountId") Integer accountId,
                          @Param("password") String password,
                          @Param("password") String paymentMethod,
                          @Param("password") Boolean active,
                          @Param("password") Boolean blocked,
                          @Param("password") SubscriptionType subscription,
                          @Param("password") LocalDateTime trialStartDate,
                          @Param("password") LocalDateTime trialEndDate,
                          @Param("password") Integer languageId,
                          @Param("password") Role role,
                          @Param("password") Integer failedAttempts,
                          @Param("password") LocalDateTime lockTime,
                          @Param("password") Boolean discount);
}