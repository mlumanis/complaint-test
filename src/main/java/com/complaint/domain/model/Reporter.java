package com.complaint.domain.model;

public record Reporter(String value) {
    public Reporter {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Reporter cannot be null or empty");
        }
        if (!value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Reporter must be a valid email address");
        }
    }

    public static Reporter of(String value) {
        return new Reporter(value);
    }
} 