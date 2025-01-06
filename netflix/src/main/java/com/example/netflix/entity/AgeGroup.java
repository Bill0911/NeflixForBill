package com.example.netflix.entity;

public enum AgeGroup
{
    SIX(6),
    NINE(9),
    TWELVE(12),
    SIXTEEN(16),
    SIXTEEN_PLUS(17);

    private final int age;

    AgeGroup(int age)
    {
        this.age = age;
    }

    public int getAge()
    {
        return this.age;
    }

}
