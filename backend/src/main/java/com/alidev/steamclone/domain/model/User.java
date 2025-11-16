package com.alidev.steamclone.domain.model;

import com.alidev.steamclone.domain.valueobjects.Email;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class User {

    private final UUID id;
    private Email email;
    private String username;
    private String passwordHash;
    private final Set<Role> roles;
    private final Set<Genre> favoriteGenres;
    private final Set<String> favoritePlatformCodes;
    private final Instant createdAt;

    public User(UUID id, Email email, String username, String passwordHash,
                Set<Role> roles, Set<Genre> favoriteGenres, Set<String> favoritePlatformCodes, Instant createdAt) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.email = Objects.requireNonNull(email);
        this.username = Objects.requireNonNull(username);
        this.passwordHash = passwordHash;
        this.roles = roles == null ? new HashSet<>() : new HashSet<>(roles);
        this.favoriteGenres = favoriteGenres == null ? new HashSet<>() : new HashSet<>(favoriteGenres);
        this.favoritePlatformCodes = favoritePlatformCodes == null ? new HashSet<>() : new HashSet<>(favoritePlatformCodes);
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
    }

    public UUID getId() { return id; }
    public Email getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Set<Role> getRoles() { return Collections.unmodifiableSet(roles); }
    public Set<Genre> getFavoriteGenres() { return Collections.unmodifiableSet(favoriteGenres); }
    public Set<String> getFavoritePlatformCodes() { return Collections.unmodifiableSet(favoritePlatformCodes); }
    public Instant getCreatedAt() { return createdAt; }

    public void updateProfile(String username, Set<Genre> favoriteGenres) {
        if (username != null && !username.isBlank()) {
            this.username = username;
        }
        if (favoriteGenres != null) {
            this.favoriteGenres.clear();
            this.favoriteGenres.addAll(favoriteGenres);
        }
    }

    public void updateEmail(Email email) {
        this.email = Objects.requireNonNull(email);
    }

    public void updatePassword(String encodedPassword) {
        this.passwordHash = Objects.requireNonNull(encodedPassword);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }

    public void toggleFavoritePlatform(String platformCode) {
        if (!favoritePlatformCodes.add(platformCode)) {
            favoritePlatformCodes.remove(platformCode);
        }
    }
}
