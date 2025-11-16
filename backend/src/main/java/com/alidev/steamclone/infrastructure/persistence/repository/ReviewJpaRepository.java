package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, UUID> {
    List<ReviewEntity> findByGameId(UUID gameId);
}
