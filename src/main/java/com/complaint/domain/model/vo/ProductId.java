package com.complaint.domain.model.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ProductId {
    private static final Pattern PATTERN = Pattern.compile("^PROD\\d{3,6}$");
    private final String value;

    private ProductId(String value) {
        this.value = value;
    }

    public static ProductId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be blank");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Product ID must start with 'PROD' followed by 3-6 digits");
        }
        return new ProductId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(value, productId.value);
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