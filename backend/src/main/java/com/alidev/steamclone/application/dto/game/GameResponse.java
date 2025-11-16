package com.alidev.steamclone.application.dto.game;

import com.alidev.steamclone.application.dto.pricing.MoneyDto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record GameResponse(UUID id,
                           String title,
                           String description,
                           MoneyDto basePrice,
                           MoneyDto currentPrice,
                           Instant releaseDate,
                           Set<String> genres,
                           Set<String> platforms) {}
