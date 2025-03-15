package com.complaint.domain.model.vo;

import java.util.Objects;

public final class ComplaintContent {
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 1000;
    private final String value;

    private ComplaintContent(String value) {
        this.value = value;
    }

    public static ComplaintContent of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Content cannot be blank");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Content must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
        }
        return new ComplaintContent(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplaintContent that = (ComplaintContent) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
} 