package com.complaint.domain.model;

public record ProductId(String value) {
    public ProductId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }
} 