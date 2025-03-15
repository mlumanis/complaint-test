package com.complaint.domain.model.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Reporter {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final String email;

    private Reporter(String email) {
        this.email = email;
    }

    public static Reporter of(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Reporter email cannot be blank");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new Reporter(email);
    }

    public String value() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reporter reporter = (Reporter) o;
        return Objects.equals(email, reporter.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return email;
    }
} 