package com.example.netflix.repository;

import com.example.netflix.dto.SubscriptionOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionCostRepository extends JpaRepository<Object, Integer> {

    @Query(value = "SELECT * FROM subscription_cost", nativeQuery = true)
    List<SubscriptionOverview> findAllSubscriptionCosts();
}
