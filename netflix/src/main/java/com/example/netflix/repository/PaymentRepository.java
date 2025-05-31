package com.example.netflix.repository;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
    Optional<Payment> findByUser_AccountId(Integer accountId);
    List<Payment> findByUser(User user);
}
