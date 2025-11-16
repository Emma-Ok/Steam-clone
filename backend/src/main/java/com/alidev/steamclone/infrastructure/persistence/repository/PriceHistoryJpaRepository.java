package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.PriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PriceHistoryJpaRepository extends JpaRepository<PriceHistoryEntity, UUID> {
    List<PriceHistoryEntity> findByGameId(UUID gameId);
}
