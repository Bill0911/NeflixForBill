package com.example.netflix.entity;

public enum SubscriptionType
{
    SD(0),
    HD(1),
    UHD(2);
    private final Integer value;

    SubscriptionType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isHigherThan(Role other) {
        return this.value > other.getValue();
    }

    public boolean isLowerThan(Role other) {
        return this.value < other.getValue();
    }
}