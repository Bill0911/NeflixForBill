package com.example.netflix.entity;

import java.util.List;

public enum Role {
    invalidValue(0),
    VIEWER(1),
    JUNIOR(3),
    MEDIOR(4),
    SENIOR(5);

    private final Integer value;

    Role(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isHigherThan(Role other) {
        return this.value > other.value;
    }

    public boolean isLowerThan(Role other) {
        return this.value < other.value;
    }
}
