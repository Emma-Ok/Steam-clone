package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRepository {
    Optional<Recommendation> findLatestByUserId(UUID userId);
    Recommendation save(Recommendation recommendation);
}
