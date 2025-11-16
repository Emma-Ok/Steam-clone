package com.alidev.steamclone.domain.model;

import com.alidev.steamclone.domain.valueobjects.Money;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Game {

    private final UUID id;
    private String title;
    private String description;
    private final Set<Genre> genres;
    private final Set<Platform> platforms;
    private Money basePrice;
    private Money currentPrice;
    private Instant releaseDate;

    public Game(UUID id, String title, String description, Set<Genre> genres, Set<Platform> platforms,
                Money basePrice, Money currentPrice, Instant releaseDate) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.genres = genres == null ? new HashSet<>() : new HashSet<>(genres);
        this.platforms = platforms == null ? new HashSet<>() : new HashSet<>(platforms);
        this.basePrice = Objects.requireNonNull(basePrice);
        this.currentPrice = currentPrice == null ? basePrice : currentPrice;
        this.releaseDate = releaseDate == null ? Instant.now() : releaseDate;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Set<Genre> getGenres() { return Collections.unmodifiableSet(genres); }
    public Set<Platform> getPlatforms() { return Collections.unmodifiableSet(platforms); }
    public Money getBasePrice() { return basePrice; }
    public Money getCurrentPrice() { return currentPrice; }
    public Instant getReleaseDate() { return releaseDate; }

    public void updateDetails(String title, String description) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
    }

    public void updatePrice(Money newPrice) {
        this.currentPrice = Objects.requireNonNull(newPrice);
    }

    public void setReleaseDate(Instant date) {
        this.releaseDate = Objects.requireNonNull(date);
    }
}
