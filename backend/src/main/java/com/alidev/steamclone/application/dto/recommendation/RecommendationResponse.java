package com.alidev.steamclone.application.dto.recommendation;

import com.alidev.steamclone.application.dto.game.GameResponse;

import java.util.List;
import java.util.UUID;

public record RecommendationResponse(UUID userId, List<GameResponse> games) {}
