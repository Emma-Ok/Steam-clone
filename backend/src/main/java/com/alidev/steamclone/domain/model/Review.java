package com.alidev.steamclone.domain.model;

import com.alidev.steamclone.domain.valueobjects.Rating;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Review {

    private final UUID id;
    private final UUID gameId;
    private final UUID userId;
    private Rating rating;
    private String comment;
    private final Instant createdAt;
    private Instant updatedAt;

    public Review(UUID id, UUID gameId, UUID userId, Rating rating, String comment, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.gameId = Objects.requireNonNull(gameId);
        this.userId = Objects.requireNonNull(userId);
        this.rating = Objects.requireNonNull(rating);
        this.comment = comment;
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt == null ? this.createdAt : updatedAt;
    }

    public UUID getId() { return id; }
    public UUID getGameId() { return gameId; }
    public UUID getUserId() { return userId; }
    public Rating getRating() { return rating; }
    public String getComment() { return comment; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void update(Rating newRating, String newComment) {
        this.rating = Objects.requireNonNull(newRating);
        this.comment = newComment;
        this.updatedAt = Instant.now();
    }
}
