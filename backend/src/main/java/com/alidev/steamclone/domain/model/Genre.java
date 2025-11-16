package com.alidev.steamclone.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Genre {

    private final UUID id;
    private final String code;
    private final String name;

    public Genre(UUID id, String code, String name) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.code = Objects.requireNonNull(code);
        this.name = Objects.requireNonNull(name);
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
