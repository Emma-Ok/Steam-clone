package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreJpaRepository extends JpaRepository<GenreEntity, UUID> {
    Optional<GenreEntity> findByCode(String code);
}
