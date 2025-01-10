package com.example.netflix.repository;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Role;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL AddUser(:email, :password, :paymentMethod, :languageId, :subscription)", nativeQuery = true)
    void addUser(@Param("email") String email,
                 @Param("password") String password,
                 @Param("paymentMethod") String paymentMethod,
                 @Param("languageId") Integer languageId,
                 @Param("subscription") SubscriptionType subscription);

    @Query(value = "CALL GetUserByEmail(:email)", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "CALL GetUserById(:accountId)", nativeQuery = true)
    Optional<User> findByAccountId(@Param("accountId") Integer accountId);

    @Query(value = "CALL GetManyUsers()", nativeQuery = true)
    List<User> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteUser(:accountId)", nativeQuery = true)
    void deleteByAccountId(@Param("accountId") Integer accountId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchUser(:accountId, :password, :paymentMethod, :active, :blocked, :subscription, :trialStartDate, :trialEndDate, :languageId, :role, :failedAttempts, :lockTime, :discount)", nativeQuery = true)
    void patchByAccountId(@Param("accountId") Integer accountId,
                          @Param("password") String password,
                          @Param("paymentMethod") String paymentMethod,
                          @Param("active") Boolean active,
                          @Param("blocked") Boolean blocked,
                          @Param("subscription") SubscriptionType subscription,
                          @Param("trialStartDate") LocalDateTime trialStartDate,
                          @Param("trialEndDate") LocalDateTime trialEndDate,
                          @Param("languageId") Integer languageId,
                          @Param("role") Role role,
                          @Param("failedAttempts") Integer failedAttempts,
                          @Param("lockTime") LocalDateTime lockTime,
                          @Param("discount") Boolean discount);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateUser(:accountId, :password, :paymentMethod, :active, :blocked, :subscription, :trialStartDate, :trialEndDate, :languageId, :role, :failedAttempts, :lockTime, :discount)", nativeQuery = true)
    void updateByAccountId(@Param("accountId") Integer accountId,
                          @Param("password") String password,
                          @Param("paymentMethod") String paymentMethod,
                          @Param("active") Boolean active,
                          @Param("blocked") Boolean blocked,
                          @Param("subscription") SubscriptionType subscription,
                          @Param("trialStartDate") LocalDateTime trialStartDate,
                          @Param("trialEndDate") LocalDateTime trialEndDate,
                          @Param("languageId") Integer languageId,
                          @Param("role") Role role,
                          @Param("failedAttempts") Integer failedAttempts,
                          @Param("lockTime") LocalDateTime lockTime,
                          @Param("discount") Boolean discount);
}