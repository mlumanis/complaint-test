package com.complaint.domain.model.vo;

import java.util.Objects;

public final class ComplaintCounter {
    private final int value;

    private ComplaintCounter(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Counter cannot be negative");
        }
        this.value = value;
    }

    public static ComplaintCounter of(int value) {
        return new ComplaintCounter(value);
    }

    public static ComplaintCounter initial() {
        return new ComplaintCounter(1);
    }

    public ComplaintCounter increment() {
        return new ComplaintCounter(value + 1);
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplaintCounter that = (ComplaintCounter) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
} 