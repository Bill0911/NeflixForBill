package com.example.netflix.repository;

import com.example.netflix.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.nextBillingDate = :nextBillingDate WHERE p.user.accountId = :userId")
    void updateNextBillingDate(Integer userId, LocalDateTime nextBillingDate);
}
