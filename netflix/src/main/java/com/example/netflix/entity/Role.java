package com.example.netflix.entity;

import java.util.List;

public enum Role {
    JUNIOR(1),
    MEDIOR(2),
    SENIOR(3);

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
