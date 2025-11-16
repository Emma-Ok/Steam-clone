package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationJpaRepository extends JpaRepository<RecommendationEntity, UUID> {
    Optional<RecommendationEntity> findTopByUserIdOrderByGeneratedAtDesc(UUID userId);
}
