package com.alidev.steamclone.application.dto.user;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserResponse(UUID id, String email, String username, Set<String> roles,
                           Set<String> favoriteGenres, Instant createdAt) {}
