package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.LibraryEntry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryRepository {
    LibraryEntry save(LibraryEntry entry);
    void deleteByUserIdAndGameId(UUID userId, UUID gameId);
    Optional<LibraryEntry> findByUserIdAndGameId(UUID userId, UUID gameId);
    List<LibraryEntry> findByUserId(UUID userId);
}
