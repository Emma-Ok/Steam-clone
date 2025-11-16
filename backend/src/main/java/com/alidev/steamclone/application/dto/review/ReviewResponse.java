package com.alidev.steamclone.application.dto.review;

import java.time.Instant;
import java.util.UUID;

public record ReviewResponse(UUID id, UUID userId, UUID gameId, int rating, String comment,
                             Instant createdAt, Instant updatedAt) {}
