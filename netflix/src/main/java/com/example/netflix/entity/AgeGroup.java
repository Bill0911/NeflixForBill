package com.example.netflix.entity;

public enum AgeGroup
{
   SIX("6"),
    NINE("9"),
    TWELVE("12"),
    SIXTEEN("16"),
    SIXTEEN_PLUS("16+");

    private final String value;

    AgeGroup(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static AgeGroup fromValue(String value) {
        for (AgeGroup ageRange : AgeGroup.values()) {
            if (ageRange.value.equals(value)) {
                return ageRange;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
