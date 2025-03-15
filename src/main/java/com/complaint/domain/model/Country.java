package com.complaint.domain.model;

public record Country(String value) {
    public Country {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }

    public static Country of(String value) {
        return new Country(value);
    }

    public static Country unknown() {
        return new Country("Unknown");
    }
} 