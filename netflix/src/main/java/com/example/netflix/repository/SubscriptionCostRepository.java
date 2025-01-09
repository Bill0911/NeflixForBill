package com.example.netflix.repository;

import com.example.netflix.dto.SubscriptionOverview;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface SubscriptionCostRepository extends JpaRepository<SubscriptionOverview, String> {

    @Query(value = "SELECT * FROM subscription_cost", nativeQuery = true)
    List<SubscriptionOverview> findAllSubscriptionCosts();
}
