package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.PlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlatformJpaRepository extends JpaRepository<PlatformEntity, UUID> {
    Optional<PlatformEntity> findByCode(String code);
}
