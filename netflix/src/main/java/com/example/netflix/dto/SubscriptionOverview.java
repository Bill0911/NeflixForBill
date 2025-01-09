package com.example.netflix.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "subscription_cost")
@Subselect("SELECT * FROM subscription_cost")
public class SubscriptionOverview implements Serializable {

    @Id
    private Integer accountId;
    private String subscription;
    private Double subscriptionCost;

    public SubscriptionOverview(Integer accountId, String subscription, Double subscriptionCost) {
        this.accountId = accountId;
        this.subscription = subscription;
        this.subscriptionCost = subscriptionCost;
    }

    public SubscriptionOverview() {

    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Double getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(Double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }
}
