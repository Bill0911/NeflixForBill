package com.example.netflix.dto;

public class SubscriptionOverview {

    private Integer accountId;
    private String email;
    private String subscriptionType;
    private String trialStatus;
    private Double subscriptionCost;

    // Constructors
    public SubscriptionOverview() {}

    public SubscriptionOverview(Integer accountId, String email, String subscriptionType, String trialStatus, Double subscriptionCost) {
        this.accountId = accountId;
        this.email = email;
        this.subscriptionType = subscriptionType;
        this.trialStatus = trialStatus;
        this.subscriptionCost = subscriptionCost;
    }

    // Getters and Setters
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(String trialStatus) {
        this.trialStatus = trialStatus;
    }

    public Double getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(Double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }
}
