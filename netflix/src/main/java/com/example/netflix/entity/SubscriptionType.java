package com.example.netflix.entity;

public enum SubscriptionType
{
    invalidValue(0),
    SD(1),
    HD(2),
    UHD(3);
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