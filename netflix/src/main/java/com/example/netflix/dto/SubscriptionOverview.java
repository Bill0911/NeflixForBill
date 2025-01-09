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

    private String email;

    private String paymentMethod;
    private Double subscriptionCost;

    public SubscriptionOverview(Integer accountId, String subscription, String email, String paymentMethod, Double subscriptionCost) {
        this.accountId = accountId;
        this.subscription = subscription;
        this.email = email;
        this.paymentMethod = paymentMethod;
        this.subscriptionCost = subscriptionCost;
    }

    public SubscriptionOverview(String email, String paymentMethod) {
        this.email = email;

        this.paymentMethod = paymentMethod;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(Double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }
}
