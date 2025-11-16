package com.alidev.steamclone.domain.model;

import com.alidev.steamclone.domain.valueobjects.Money;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class PriceHistory {

    private final UUID id;
    private final UUID gameId;
    private final Money price;
    private final Instant changedAt;

    public PriceHistory(UUID id, UUID gameId, Money price, Instant changedAt) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.gameId = Objects.requireNonNull(gameId);
        this.price = Objects.requireNonNull(price);
        this.changedAt = changedAt == null ? Instant.now() : changedAt;
    }

    public UUID getId() { return id; }
    public UUID getGameId() { return gameId; }
    public Money getPrice() { return price; }
    public Instant getChangedAt() { return changedAt; }
}
