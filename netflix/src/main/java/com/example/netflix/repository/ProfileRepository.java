package com.example.netflix.repository;

import com.example.netflix.entity.Profile;
import com.example.netflix.entity.Role;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL AddProfile(:accountId, :profileImage, :age, :name)", nativeQuery = true)
    void addProfile(@Param("accountId") Integer accountId,
                    @Param("profileImage") String profileImage,
                    @Param("age") Integer age,
                    @Param("name") String name);

    @Query(value = "CALL GetProfileById(:profileId)", nativeQuery = true)
    Optional<Profile> findByProfileId(@Param("profileId") Integer profileId);

    @Query(value = "CALL GetManyProfiles()", nativeQuery = true)
    List<Profile> findMany();

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteProfile(:profileId)", nativeQuery = true)
    void deleteByProfileId(@Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query(value = "CALL PatchProfile(:profileId, :accountId, :profileImage, :age, :name)", nativeQuery = true)
    void patchByProfileId(@Param("profileId") Integer profileId,
                          @Param("accountId") Integer accountId,
                          @Param("profileImage") String profileImage,
                          @Param("age") Integer age,
                          @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateProfile(:profileId, :accountId, :profileImage, :age, :name)", nativeQuery = true)
    void updateByProfileId(@Param("profileId") Integer profileId,
                           @Param("accountId") Integer accountId,
                           @Param("profileImage") String profileImage,
                           @Param("age") Integer age,
                           @Param("name") String name);
}