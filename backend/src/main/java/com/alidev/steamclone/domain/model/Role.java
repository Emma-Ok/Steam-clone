package com.alidev.steamclone.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Role {

    private final UUID id;
    private final String name;

    public Role(UUID id, String name) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.name = Objects.requireNonNull(name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
