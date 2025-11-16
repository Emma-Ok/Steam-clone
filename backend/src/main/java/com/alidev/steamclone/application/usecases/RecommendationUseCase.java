package com.alidev.steamclone.application.usecases;

import com.alidev.steamclone.application.dto.recommendation.RecommendationResponse;

import java.util.UUID;

public interface RecommendationUseCase {
    RecommendationResponse recommendForUser(UUID userId);
}
