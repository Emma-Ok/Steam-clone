package com.alidev.steamclone.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email {
        Objects.requireNonNull(value, "email cannot be null");
        if (!REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static Email of(String value) {
        return new Email(value.toLowerCase());
    }
}
