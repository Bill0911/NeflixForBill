package com.example.netflix.repository.crud;

import com.example.netflix.dto.SubscriptionOverview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubscriptionOverviewRepository extends CrudRepository<Object, Long> {

    @Query(value = "SELECT * FROM SubscriptionOverview", nativeQuery = true)
    List<SubscriptionOverview> findAllSubscriptions();
}