package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameEntity, UUID> {
    List<GameEntity> findDistinctByGenres_CodeIn(List<String> codes);
}
