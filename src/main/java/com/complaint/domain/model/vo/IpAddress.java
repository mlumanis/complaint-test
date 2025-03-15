package com.complaint.domain.model.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class IpAddress {
    private static final Pattern IPV4_PATTERN = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    private final String value;

    private IpAddress(String value) {
        this.value = value;
    }

    public static IpAddress of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("IP address cannot be blank");
        }
        if (!IPV4_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid IP address format");
        }
        return new IpAddress(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpAddress ipAddress = (IpAddress) o;
        return Objects.equals(value, ipAddress.value);
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