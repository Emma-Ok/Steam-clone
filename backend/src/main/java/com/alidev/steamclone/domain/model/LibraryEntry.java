package com.alidev.steamclone.domain.model;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class LibraryEntry {

    public enum Status { OWNED, WISHLIST, FAVORITE }

    private final UUID id;
    private final UUID userId;
    private final UUID gameId;
    private final Set<Status> statuses;
    private int progressMinutes;
    private final Instant addedAt;

    public LibraryEntry(UUID id, UUID userId, UUID gameId, Set<Status> statuses, int progressMinutes, Instant addedAt) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.userId = Objects.requireNonNull(userId);
        this.gameId = Objects.requireNonNull(gameId);
        this.statuses = statuses == null ? EnumSet.noneOf(Status.class) : EnumSet.copyOf(statuses);
        this.progressMinutes = progressMinutes;
        this.addedAt = addedAt == null ? Instant.now() : addedAt;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getGameId() { return gameId; }
    public Set<Status> getStatuses() { return EnumSet.copyOf(statuses); }
    public int getProgressMinutes() { return progressMinutes; }
    public Instant getAddedAt() { return addedAt; }

    public void toggleStatus(Status status) {
        if (!statuses.add(status)) {
            statuses.remove(status);
        }
    }

    public void updateProgress(int minutes) {
        this.progressMinutes = minutes;
    }

    public void replaceStatuses(Set<Status> newStatuses) {
        statuses.clear();
        statuses.addAll(newStatuses);
    }
}
