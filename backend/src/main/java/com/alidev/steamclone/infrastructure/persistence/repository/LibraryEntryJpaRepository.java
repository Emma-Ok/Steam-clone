package com.alidev.steamclone.infrastructure.persistence.repository;

import com.alidev.steamclone.infrastructure.persistence.entity.LibraryEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryEntryJpaRepository extends JpaRepository<LibraryEntryEntity, UUID> {
    void deleteByUserIdAndGameId(UUID userId, UUID gameId);
    Optional<LibraryEntryEntity> findByUserIdAndGameId(UUID userId, UUID gameId);
    List<LibraryEntryEntity> findByUserId(UUID userId);
}
